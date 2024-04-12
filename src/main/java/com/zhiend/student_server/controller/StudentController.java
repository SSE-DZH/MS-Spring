package com.zhiend.student_server.controller;

import com.zhiend.student_server.dto.EmailVerificationDto;
import com.zhiend.student_server.dto.RegisterDTO;
import com.zhiend.student_server.dto.LoginDTO;
import com.zhiend.student_server.dto.UpdatePasswordDTO;
import com.zhiend.student_server.entity.Student;
import com.zhiend.student_server.result.Result;
import com.zhiend.student_server.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 学生相关操作的Controller
 * @Auther: zhiend
 * @Date: 2024/4/8
 * @Description: 学生信息管理
 * @Version 1.0.0
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/student")
@Api(tags = "学生信息管理")
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * 根据邮箱验证代码检查邮箱对应的验证码是否正确。
     *
     * @param emailVerificationDto 包含邮箱和验证码信息的数据传输对象。
     * @return 返回操作结果，如果验证码正确，则返回操作成功的结果；否则返回操作失败并附带错误信息。
     */
    @PostMapping("/checkCode")
    @ApiOperation("根据邮箱验证代码检查邮箱对应的验证码是否正确")
    public Result checkCode(@RequestBody EmailVerificationDto emailVerificationDto) {
        // 尝试根据提供的信息验证验证码的正确性
        boolean result = studentService.checkCode(emailVerificationDto);
        // 根据验证结果返回相应的操作结果
        if (result) {
            return Result.success();
        } else {
            return Result.error("验证码错误");
        }
    }



    /**
     * 更改密码
     *
     * @param updatePasswordDTO 包含新密码和其他必要信息的数据传输对象
     * @return 返回操作结果，如果成功则包含成功信息，如果失败则包含错误信息
     */
    @ApiOperation("更改密码")
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        // 调用学生服务层的方法，尝试根据提供的信息更新学生密码
        boolean result = studentService.updateByCode(updatePasswordDTO);
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
        String email = studentService.findByUsername(username).getEmail();
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
     * 添加学生信息
     *
     * @param student 学生对象，包含学生详细信息，通过RequestBody接收前端传来的JSON数据。
     * @return boolean 返回添加结果，true表示添加成功，false表示添加失败。
     */
    @ApiOperation("添加学生")
    @PostMapping("/addStudent")
    public boolean addStudent(@RequestBody Student student) {
        // 打印保存学生信息的提示
        System.out.println("正在保存学生对象" + student);
        // 调用学生服务层的save方法保存学生信息，并返回保存结果
        return studentService.save(student);
    }

    /**
     * 发送邮箱验证码
     *
     * @param email 需要发送验证码的邮箱地址
     * @return ResponseEntity<Result<String>> 如果发送成功，返回包含成功信息的ResponseEntity；如果发送失败，返回包含错误信息的ResponseEntity。
     */
    @ApiOperation("发送邮箱验证码")
    @PostMapping("/sendEailCode")
    public ResponseEntity<Result<String>> sendMailCode(@RequestParam("email") String email) {
        try {
            // 尝试发送邮箱验证码
            studentService.sendMailCode(email);
            // 邮箱验证码发送成功，返回成功提示信息
            return ResponseEntity.ok(Result.success("验证码已发送至邮箱：" + email));
        } catch (Exception e) {
            // 发送邮箱验证码失败，返回错误提示信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.error("发送验证码失败：" + e.getMessage()));
        }
    }

    /**
     * 用户注册接口
     *
     * @param registerDTO 包含注册信息的数据传输对象，例如用户名、密码等
     * @return ResponseEntity<Result<String>> 如果注册成功，返回带有成功消息的ResponseEntity；如果注册失败，返回带有错误消息的ResponseEntity；如果遇到异常，返回带有异常信息的错误消息。
     */
    @ApiOperation("注册")
    @PostMapping(value = "/register")
    public ResponseEntity<Result<String>> register(@RequestBody RegisterDTO registerDTO) {
        try {
            // 尝试注册用户
            boolean success = studentService.register(registerDTO);
            if (success) {
                // 注册成功，返回成功消息
                return ResponseEntity.ok(Result.success("注册成功！"));
            } else {
                // 注册失败，返回错误消息
                return ResponseEntity.ok(Result.error("注册失败"));
            }
        } catch (Exception e) {
            // 遇到异常，返回异常信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.error("注册失败：" + e.getMessage()));
        }



    }

    @ApiOperation("学生登录")
    @PostMapping("/login")
    public boolean login(@RequestBody LoginDTO loginDTO) {
        System.out.println("正在验证学生登录 " + loginDTO);
        // 通过用户名查询学生信息
        Student student = studentService.findByUsername(loginDTO.getUsername());
        if (student != null) {
            // 判断密码是否匹配
            return student.getPassword().equals(loginDTO.getPassword());
        } else {
            // 如果学生信息为null，说明用户名不存在，直接返回false
            return false;
        }
    }


    @ApiOperation("根据条件查询学生信息")
    @PostMapping("/findBySearch")
    public List<Student> findBySearch(@RequestBody Student student) {
        Integer fuzzy = (student.getPassword() == null) ? 0 : 1;
        return studentService.findBySearch(student.getSid(), student.getSname(), fuzzy);
    }

    @ApiOperation("根据学生ID查询学生信息")
    @GetMapping("/findById/{sid}")
    public Student findById(@PathVariable("sid") Integer sid) {
        System.out.println("正在查询学生信息 By id " + sid);
        return studentService.findById(sid);
    }

    @ApiOperation("根据用户名查询学生信息")
    @GetMapping("/findByUsername/{username}")
    public Student findByUsername(@PathVariable String username) {
        return studentService.findByUsername(username);
    }


    @ApiOperation("分页查询学生信息")
    @GetMapping("/findByPage/{page}/{size}")
    public List<Student> findByPage(@PathVariable("page") int page, @PathVariable("size") int size) {
        System.out.println("查询学生列表分页 " + page + " " + size);
        return studentService.findByPage(page, size);
    }

    @ApiOperation("获取学生总数")
    @GetMapping("/getLength")
    public Integer getLength() {
        return studentService.getLength();
    }

    /**
     * 根据学生ID删除学生信息。
     *
     * @param sid 学生的ID，用于指定要删除的学生。
     * @return 返回一个布尔值，表示删除操作是否成功。
     */
    @ApiOperation("根据学生ID删除学生信息")
    @GetMapping("/deleteById/{sid}")
    public boolean deleteById(@PathVariable("sid") int sid) {
        System.out.println("正在删除学生 sid：" + sid);
        return studentService.deleteById(sid);
    }

    /**
     * 更新学生信息。
     *
     * @param student 包含更新后学生信息的对象，通过RequestBody接收前端传来的JSON数据。
     * @return 返回一个布尔值，表示更新操作是否成功。
     */
    @ApiOperation("更新学生信息")
    @PostMapping("/updateStudent")
    public boolean updateStudent(@RequestBody Student student) {
        System.out.println("更新 " + student);
        return studentService.updateById(student);
    }
}
