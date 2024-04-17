package com.zhiend.student_server.controller;

import com.zhiend.student_server.constant.MessageConstant;
import com.zhiend.student_server.dto.LoginDTO;
import com.zhiend.student_server.dto.PageDTO;
import com.zhiend.student_server.dto.RegisterDTO;
import com.zhiend.student_server.dto.UpdatePasswordDTO;
import com.zhiend.student_server.entity.Student;
import com.zhiend.student_server.entity.Teacher;
import com.zhiend.student_server.exception.PasswordErrorException;
import com.zhiend.student_server.result.Result;
import com.zhiend.student_server.service.TeacherService;
import com.zhiend.student_server.utils.JwtUtils;
import com.zhiend.student_server.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教师控制器
 * @Auther: zhiend
 * @Date: 2024/4/9
 * @Description: 教师控制器
 * @Version 1.0.0
 */

@RestController
@CrossOrigin("*")
@RequestMapping("/teacher")
@Api(tags = "教师管理")
@Slf4j
public class TeacherController {
    @Autowired
    private TeacherService teacherService;


    /**
     * 更改密码
     *
     * @param updatePasswordDTO 包含新密码和其他必要信息的数据传输对象
     * @return 返回操作结果，如果成功则包含成功信息，如果失败则包含错误信息
     */
    @ApiOperation("更改密码")
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        // 调用教师服务层的方法，尝试根据提供的信息更新学生密码
        boolean result = teacherService.updateByCode(updatePasswordDTO);
        // 根据更新结果返回相应的操作结果
        if (result) {
            return Result.success();
        } else {
            return Result.error("更新密码失败");
        }
    }

    /**
     * 根据用户名查找并返回对应的邮箱地址。
     *
     * @param username 用户名，用于查找对应的邮箱地址。
     * @return 返回一个结果对象，如果找到对应的邮箱地址，则返回邮箱地址；否则，返回错误信息。
     */
    @ApiOperation("根据username查找返回邮箱")
    @GetMapping("/findEmailByUsername")
    public Result<String> findEmailByUsername(@RequestParam("username") String username) {
        System.out.println("根据姓名查找邮箱");
        // 调用学生服务层，根据用户名查找邮箱地址
        String email = teacherService.findByUsername(username).getEmail();
        // 判断是否找到对应的邮箱地址
        if (email != null) {
            // 找到邮箱，返回成功结果包含邮箱地址
            return Result.success(email);
        } else {
            // 未找到邮箱，返回错误结果
            return Result.error("未找到邮箱");
        }
    }

    /**
     * 添加教师
     * @param teacher 教师对象
     * @return 添加结果，成功为true，失败为false
     */
    @PostMapping("/addTeacher")
    @ApiOperation("添加教师")
    public boolean addTeacher(@RequestBody Teacher teacher) {
        log.info("添加教师：{}", teacher);
        teacherService.save(teacher);
        return true;
    }

    @ApiOperation("发送邮箱验证码")
    @PostMapping("/sendEailCode")
    public ResponseEntity<Result<String>> sendMailCode(@RequestParam("email") String email) {
        try {
            teacherService.sendMailCode(email);
            return ResponseEntity.ok(Result.success("验证码已发送至邮箱：" + email));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.error("发送验证码失败：" + e.getMessage()));
        }
    }

    @ApiOperation("注册")
    @PostMapping(value = "/register")
    public ResponseEntity<Result<String>> register(@RequestBody RegisterDTO registerDTO) {
        try {
            boolean success = teacherService.register(registerDTO);
            if (success) {
                return ResponseEntity.ok(Result.success("注册成功！"));
            } else {
                return ResponseEntity.ok(Result.error("注册失败"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.error("注册失败：" + e.getMessage()));
        }
    }

    /**
     * 教师登录验证
     * @param loginDTO 登录对象
     * @return 登录结果，成功为true，失败为false
     */
    @PostMapping("/login")
    @ApiOperation("教师登录")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        System.out.println("正在验证教师登录 " + loginDTO);
        // 通过用户名查询教师信息
        Teacher teacher = teacherService.findByUsername(loginDTO.getUsername());
        String password = loginDTO.getPassword();

        //密码比对
        //对前端传过来的明文密码进行md5加密处理
        String passwordmd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(teacher.getPassword()) && !passwordmd5.equals(teacher.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", teacher.getTid());
        claims.put("username", teacher.getTname());
        String token = JwtUtils.generateJwt(claims);

        //loginvo的builder
        LoginVO loginVO = LoginVO.builder()
                .token(token)
                .build();
        return Result.success(loginVO);
    }

    /**
     * 根据教师ID查询教师信息
     * @param tid 教师ID
     * @return 教师对象
     */
    @GetMapping("/findById/{tid}")
    @ApiOperation("根据ID查询教师信息")
    public Teacher findById(@PathVariable("tid") Integer tid) {
        return teacherService.findById(tid);
    }

    @ApiOperation("根据用户名查询教师信息")
    @GetMapping("/findByUsername/{username}")
    public Teacher findByUsername(@PathVariable String username) {
        return teacherService.findByUsername(username);
    }


    /**
     * 根据条件查询教师信息
     * @param map 查询条件，包含教师姓名和模糊查询标志
     * @return 符合条件的教师列表
     */
    @PostMapping("/findBySearch")
    @ApiOperation("根据条件查询教师信息")
    public List<Teacher> findBySearch(@RequestBody Map<String, String> map) {
        return teacherService.findBySearch(map);
    }


    /**
     * 根据教师ID删除教师信息
     * @param tid 教师ID
     * @return 删除结果，成功为true，失败为false
     */
    @GetMapping("/deleteById/{tid}")
    @ApiOperation("根据ID删除教师信息")
    public boolean deleteById(@PathVariable("tid") int tid) {
        return teacherService.deleteById(tid);
    }

    /**
     * 更新教师信息
     * @param teacher 教师对象
     * @return 更新结果，成功为true，失败为false
     */
    @PostMapping("/updateTeacher")
    @ApiOperation("更新教师信息")
    public boolean updateTeacher(@RequestBody Teacher teacher) {
        log.info("更新教师信息：{}", teacher);
        teacherService.updateById(teacher);
        return true;
    }
}
