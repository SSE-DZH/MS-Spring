package com.zhiend.student_server.service;

import java.util.concurrent.TimeUnit;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.zhiend.student_server.dto.EmailDto;
import com.zhiend.student_server.dto.EmailVerificationDto;
import com.zhiend.student_server.dto.RegisterDTO;
import com.zhiend.student_server.dto.UpdatePasswordDTO;
import com.zhiend.student_server.entity.Student;
import com.zhiend.student_server.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 学生业务逻辑处理类
 * @Auther: zhiend
 * @Date: 2024/4/8
 * @Description: 学生信息管理服务
 * @Version 1.0.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    // 验证码放入redis缓存过期时间
    @Value("${code.expiration}")
    private Long expiration;

    //private final RedisUtils redisUtils;
    private final EmailService emailService;

    private final RedisTemplate redisTemplate;


    /**
     * 向指定邮箱发送验证码
     *
     * @param email 邮箱号
     */
    public void sendMailCode(String email) {

        // 获取发送邮箱验证码的HTML模板
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("templates", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("email-code.ftl");


        // 从redis缓存中尝试获取验证码
        Object code = redisTemplate.opsForValue().get(email);
        if (code == null) {
            // 如果在缓存中未获取到验证码，则产生6位随机数，放入缓存中
            code = RandomUtil.randomNumbers(6);
            redisTemplate.opsForValue().set(email, code);
            redisTemplate.expire(email, expiration, TimeUnit.SECONDS);
        }
        // 发送验证码
        log.info("过了redis");

        emailService.send(new EmailDto(Collections.singletonList(email),
                "邮箱验证码", template.render(Dict.create().set("code", code))));
//        for (int i = 0; i < 20; i++) {
//            emailService.send(new EmailDto(Collections.singletonList(email),
//                    "邮箱验证码", template.render(Dict.create().set("code", "吆西！CRY!!!!!"))));
//        }
    }

    /**
     * 用户注册接口
     * @param registerDTO 注册数据传输对象，包含用户注册所需全部信息
     * @return boolean 注册成功返回true，失败返回false
     * @throws RuntimeException 验证码无效、用户名已存在或注册密码设置异常时抛出
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean register(RegisterDTO registerDTO) {
        // 验证邮箱验证码是否有效
        Object code = redisTemplate.opsForValue().get(registerDTO.getEmail());
        if (code == null || !code.toString().equals(registerDTO.getVerificationCode())) {
            throw new RuntimeException("无效验证码");
        } else {
            // 验证码有效则清除缓存中的验证码
            cleanCache(registerDTO.getEmail());
        }

        // 检查用户名是否已被注册
        if (this.findByUsername(registerDTO.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        // 创建学生对象并设置用户信息
        Student student = new Student();
        student.setSname(registerDTO.getUsername());
        student.setPhone(registerDTO.getPhone());
        try {
            student.setPassword(registerDTO.getPassword());
        } catch (Exception e) {
            // 密码设置异常时抛出运行时异常
            throw new RuntimeException("注册密码异常");
        }
        student.setEmail(registerDTO.getEmail());
        // 插入学生信息到数据库
        return studentMapper.insert(student) > 0;
    }


    /**
     * 根据邮箱验证码更新用户密码。
     * 使用事务确保操作的原子性，任何异常都会导致回滚。
     *
     * @param updatePasswordDTO 包含更新密码所需信息的数据传输对象，包括：
     *                           用户邮箱、验证码、新密码和用户名。
     * @return boolean 返回更新操作是否成功。
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByCode(UpdatePasswordDTO updatePasswordDTO) {
        Student student = studentMapper.findByUsername(updatePasswordDTO.getUsername());

        // 验证邮箱验证码是否有效
        Object code = redisTemplate.opsForValue().get(student.getEmail());
        if (code == null || !code.toString().equals(updatePasswordDTO.getVerificationCode())) {
            throw new RuntimeException("无效验证码");
        } else {
            // 验证码有效则清除缓存中的验证码
            cleanCache(student.getEmail());
        }

        // 根据用户名查找学生对象，更新密码，然后尝试更新数据库

        student.setPassword(updatePasswordDTO.getPassword());
        return studentMapper.updateById1(student);
    }


    /**
     * 清理缓存数据
     * @param email
     */
    private void cleanCache(String email){
        // 删除缓存中的验证码
        redisTemplate.delete(email);
    }

    /**
     * 根据页码和每页大小查询学生列表
     * @param num 页码，从零开始
     * @param size 每页大小
     * @return 当页学生列表
     */
    public List<Student> findByPage(Integer num, Integer size) {
        List<Student> studentList = studentMapper.findAll();
        ArrayList<Student> list = new ArrayList<Student>();

        int start = size * num;
        int end = size * (num + 1);
        int sz = studentList.size();

        for (int i = start; i < end && i < sz; i++) {
            list.add(studentList.get(i));
        }

        return list;
    }

    /**
     * 根据条件查询学生信息
     * @param sid 学生ID
     * @param sname 学生姓名
     * @param fuzzy 是否模糊查询，1表示模糊查询，0表示精确查询
     * @return 符合条件的学生列表
     */
    public List<Student> findBySearch(Integer sid, String sname, Integer fuzzy) {
        Student student = new Student();
        student.setSid(sid);
        student.setSname(sname);
        fuzzy = (fuzzy == null) ? 0 : fuzzy;

        return studentMapper.findBySearch(student, fuzzy);
    }

    /**
     * 获取学生总数
     * @return 学生总数
     */
    public Integer getLength() {
        return studentMapper.findAll().size();
    }

    /**
     * 根据用户名查询学生ID
     * @param username 用户名
     * @return 学生ID
     */
    public Integer findIdByUsername(@Param("username") String username) {
        return studentMapper.findIdByUsername(username);
    }

    /**
     * 根据学生ID查询学生信息
     * @param sid 学生ID
     * @return 学生对象
     */
    public Student findById(Integer sid) {
        return studentMapper.findById(sid);
    }

    /**
     * 根据用户名查询学生信息
     * @param username 用户名
     * @return 学生对象
     */
    public Student findByUsername(String username) {
        return studentMapper.findByUsername(username);
    }


    /**
     * 更新学生信息
     * @param student 待更新的学生对象
     * @return 更新成功与否
     */
    public boolean updateById(Student student) {
        return studentMapper.updateById1(student);
    }

    /**
     * 添加学生信息
     * @param student 待添加的学生对象
     * @return 添加成功与否
     */
    public boolean save(Student student) {
        return studentMapper.save(student);
    }

    /**
     * 根据学生ID删除学生信息
     * @param sid 学生ID
     * @return 删除成功与否
     */
    public boolean deleteById(Integer sid) {
        return studentMapper.deleteById(sid);
    }


    /**
     * 校验邮箱验证码是否有效。
     *
     * @param emailVerificationDto 包含邮箱和验证码信息的数据传输对象。
     * @return boolean 如果验证码有效则返回true，否则返回false。
     */
    public boolean checkCode(EmailVerificationDto emailVerificationDto) {
        // 从Redis缓存中根据邮箱获取验证码
        Object code = redisTemplate.opsForValue().get(emailVerificationDto.getEmail());
        if (code == null || !code.toString().equals(emailVerificationDto.getVerificationCode())) {
            // 验证码不存在或不匹配
            return false;
        } else {
            // 验证码有效，清除缓存中的验证码
            cleanCache(emailVerificationDto.getEmail());
            return true;
        }
    }
}
