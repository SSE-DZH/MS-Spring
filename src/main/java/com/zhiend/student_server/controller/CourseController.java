package com.zhiend.student_server.controller;

import com.zhiend.student_server.entity.Course;
import com.zhiend.student_server.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhiend
 * @Date: 2024/04/08
 * @Description: 课程管理控制器类
 * @Version: 1.0.0
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/course")
@Api(tags = "课程管理")
public class CourseController {
    @Autowired
    private CourseService courseService;

    /**
     * 获取所有课程名字
     *
     * @return 返回一个包含所有课程名字的List<String>
     */
    @ApiOperation("获取所有课程名字")
    @GetMapping("/findAllCname")
    public List<String> findAllCname() {
        // 从courseService中查询并返回所有课程名字
        return courseService.findAllCname();
    }

    /**
     * 根据给定参数搜索课程
     *
     * @param map 搜索课程的参数
     * @return 匹配搜索条件的课程列表
     */
    @ApiOperation("搜索课程")
    @PostMapping("/findBySearch")
    public List<Course> findBySearch(@RequestBody Map<String, String> map) {
        return courseService.findBySearch(map);
    }

    /**
     * 根据课程ID检索课程
     *
     * @param cid 要检索的课程ID
     * @return 包含指定ID课程的列表
     */
    @ApiOperation("根据ID获取课程")
    @GetMapping("/findById/{cid}")
    public List<Course> findById(@PathVariable Integer cid) {
        return courseService.findBySearch(cid);
    }

    /**
     * 保存新课程
     *
     * @param course 要保存的课程对象
     * @return 如果课程成功保存，则为true，否则为false
     */
    @ApiOperation("保存新课程")
    @PostMapping("/save")
    public boolean save(@RequestBody Course course) {
        System.out.println(course);
        return courseService.insertCourse(course);
    }

    /**
     * 根据ID删除课程
     *
     * @param cid 要删除的课程的ID
     * @return 如果课程成功删除，则为true，否则为false
     */
    @ApiOperation("根据ID删除课程")
    @GetMapping("/deleteById/{cid}")
    public boolean deleteById(@PathVariable Integer cid) {
        System.out.println("正在删除课程 ID: " + cid);
        return courseService.deleteById(cid);
    }

    /**
     * 更新现有课程
     *
     * @param course 更新后的课程对象
     * @return 如果课程成功更新，则为true，否则为false
     */
    @ApiOperation("更新课程")
    @PostMapping("/updateCourse")
    public boolean updateCourse(@RequestBody Course course) {
        System.out.println("正在修改课程: " + course);
        return courseService.updateById(course);
    }
}
