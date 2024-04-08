package com.zhiend.student_server.controller;

import com.zhiend.student_server.entity.Student;
import com.zhiend.student_server.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @ApiOperation("添加学生")
    @PostMapping("/addStudent")
    public boolean addStudent(@RequestBody Student student) {
        System.out.println("正在保存学生对象" + student);
        return studentService.save(student);
    }

    @ApiOperation("学生登录")
    @PostMapping("/login")
    public boolean login(@RequestBody Student student) {
        System.out.println("正在验证学生登录 " + student);
        Student s = studentService.findById(student.getSid());
        return s != null && s.getPassword().equals(student.getPassword());
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

    @ApiOperation("根据学生ID删除学生信息")
    @GetMapping("/deleteById/{sid}")
    public boolean deleteById(@PathVariable("sid") int sid) {
        System.out.println("正在删除学生 sid：" + sid);
        return studentService.deleteById(sid);
    }

    @ApiOperation("更新学生信息")
    @PostMapping("/updateStudent")
    public boolean updateStudent(@RequestBody Student student) {
        System.out.println("更新 " + student);
        return studentService.updateById(student);
    }
}
