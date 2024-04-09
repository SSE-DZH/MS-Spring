package com.zhiend.student_server.controller;

import com.zhiend.student_server.dto.LoginDTO;
import com.zhiend.student_server.dto.PageDTO;
import com.zhiend.student_server.entity.Student;
import com.zhiend.student_server.entity.Teacher;
import com.zhiend.student_server.query.PageQuery;
import com.zhiend.student_server.result.Result;
import com.zhiend.student_server.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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

    /**
     * 教师登录验证
     * @param loginDTO 登录对象
     * @return 登录结果，成功为true，失败为false
     */
    @PostMapping("/login")
    @ApiOperation("教师登录")
    public boolean login(@RequestBody LoginDTO loginDTO) {
        System.out.println("正在验证教师登录 " + loginDTO);
        // 通过用户名查询教师信息
        Teacher teacher = teacherService.findByUsername(loginDTO.getUsername());
        if (teacher != null) {
            // 判断密码是否匹配
            return teacher.getPassword().equals(loginDTO.getPassword());
        } else {
            // 如果教师信息为null，说明用户名不存在，直接返回false
            return false;
        }
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
