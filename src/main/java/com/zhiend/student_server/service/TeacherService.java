package com.zhiend.student_server.service;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.zhiend.student_server.dto.EmailDto;
import com.zhiend.student_server.dto.RegisterDTO;
import com.zhiend.student_server.entity.Student;
import com.zhiend.student_server.entity.Teacher;
import com.zhiend.student_server.mapper.TeacherMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 教师服务
 * @Auther: zhiend
 * @Date: 2024/4/9
 * @Description: 教师服务
 * @Version 1.0.0
 */

@Service
@Api(tags = "教师服务")
@Slf4j
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

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
     * 注册功能
     * @param registerDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean register(RegisterDTO registerDTO) {
        // 通过email获取redis中的code
        Object code = redisTemplate.opsForValue().get(registerDTO.getEmail());
        if (code == null || !code.toString().equals(registerDTO.getCheckCode())) {
            throw new RuntimeException("无效验证码");
        } else {
            cleanCache(registerDTO.getEmail());
        }

        if (this.findByUsername(registerDTO.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        // 创建用户
        Teacher teacher = new Teacher();
        teacher.setTname(registerDTO.getUsername());
        teacher.setPhone(registerDTO.getPhone());
        try {
            teacher.setPassword(registerDTO.getPassword());
        } catch (Exception e) {
            throw new RuntimeException("注册密码异常");
        }
        teacher.setEmail(registerDTO.getEmail());
        //studentMapper.insert(teacher);
        return teacherMapper.insert(teacher) > 0;
        //return this.create(teacher) != null;
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
     * 根据条件查询教师信息
     * @param map 查询条件，包含教师ID、姓名和模糊查询标志
     * @return 符合条件的教师列表
     */
    @ApiOperation("根据条件查询教师信息")
    public List<Teacher> findBySearch(Map<String, String> map) {
        Integer tid = null;
        String tname = null;
        Integer fuzzy = null;
        if (map.containsKey("tid")) {
            try {
                tid = Integer.parseInt(map.get("tid"));
            } catch (Exception e) {
            }
        }
        if (map.containsKey("tname")) {
            tname = map.get("tname");
        }
        if (map.containsKey("fuzzy")) {
            fuzzy = map.get("fuzzy").equals("true") ? 1 : 0;
        }
        System.out.println(map);
        System.out.println("查询类型：" + tid + ", " + tname + ", " + fuzzy);
        return teacherMapper.findBySearch(tid, tname, fuzzy);
    }

    /**
     * 根据用户名查询教师信息
     * @param username 用户名
     * @return 教师对象
     */
    public Teacher findByUsername(String username) {
        return teacherMapper.findByUsername(username);
    }


    /**
     * 分页查询教师信息
     * @param num 当前页数
     * @param size 每页大小
     * @return 分页后的教师列表
     */
    @ApiOperation("分页查询教师信息")
    public List<Teacher> findByPage(Integer num, Integer size) {
        List<Teacher> teacherList = teacherMapper.findAll();
        ArrayList<Teacher> list = new ArrayList<>();

        int start = size * num;
        int end = size * (num + 1);
        int sz = teacherList.size();

        for (int i = start; i < end && i < sz; i++) {
            list.add(teacherList.get(i));
        }

        return list;
    }

    /**
     * 获取教师总数
     * @return 教师总数
     */
    @ApiOperation("获取教师总数")
    public Integer getLength() {
        return teacherMapper.findAll().size();
    }

    /**
     * 根据教师ID查询教师信息
     * @param tid 教师ID
     * @return 对应教师信息
     */
    @ApiOperation("根据ID查询教师信息")
    public Teacher findById(Integer tid) {
        return teacherMapper.findById(tid);
    }

    /**
     * 更新教师信息
     * @param teacher 教师对象
     * @return 更新结果，成功为true，失败为false
     */
    @ApiOperation("更新教师信息")
    public void updateById(Teacher teacher) {
        teacherMapper.updateById1(teacher);
    }

    /**
     * 添加教师信息
     * @param teacher 教师对象
     * @return 添加结果，成功为true，失败为false
     */
    @ApiOperation("添加教师信息")
    public void save(Teacher teacher) {
        teacherMapper.save(teacher);
    }

    /**
     * 根据教师ID删除教师信息
     * @param tid 教师ID
     * @return 删除结果，成功为true，失败为false
     */
    @ApiOperation("根据ID删除教师信息")
    public boolean deleteById(Integer tid) {
        return teacherMapper.deleteById(tid);
    }
}
