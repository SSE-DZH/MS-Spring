package com.zhiend.student_server.controller;

import com.zhiend.student_server.entity.Course;
import com.zhiend.student_server.entity.CourseTeacher;
import com.zhiend.student_server.entity.CourseTeacherInfo;
import com.zhiend.student_server.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther: zhiend
 * @Date: 2024/04/08
 * @Description: 课程教师控制器
 * @Version 1.0.0
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/courseTeacher")
@Api(tags = "课程教师管理")
public class CourseTeacherController {
    @Autowired
    private CourseTeacherService courseTeacherService;

    /**
     * 添加课程教师
     * @param cid 课程ID
     * @param tid 教师ID
     * @param term 学期
     * @return 是否成功添加
     */
    @GetMapping("/insert/{cid}/{tid}/{term}/{location}/{schedule}")
    @ApiOperation(value = "添加课程教师")
    public boolean insert(@PathVariable Integer cid,
                          @PathVariable Integer tid,
                          @PathVariable String term,
                          @PathVariable String location,
                          @PathVariable String schedule) {
        if (courseTeacherService.findBySearch(cid, tid, term).size() != 0) {
            return false;
        }
        return courseTeacherService.insertCourseTeacher(cid, tid, term, location, schedule);
    }


    /**
     * 查询教师开设的课程
     * @param tid 教师ID
     * @param term 学期
     * @return 教师开设的课程列表
     */
    @GetMapping("/findMyCourse/{tid}/{term}")
    @ApiOperation(value = "查询教师开设的课程")
    public List<Course> findMyCourse(@PathVariable Integer tid, @PathVariable String term) {
        System.out.println("查询教师课程：" + tid + " " + term);
        return courseTeacherService.findMyCourse(tid, term);
    }

    /**
     * 根据条件查询课程教师信息
     * @param map 查询条件
     * @return 符合条件的课程教师信息列表
     */
    @PostMapping("/findCourseTeacherInfo")
    @ApiOperation(value = "根据条件查询课程教师信息")
    public List<CourseTeacherInfo> findCourseTeacherInfo(@RequestBody Map<String, String> map) {
        return courseTeacherService.findCourseTeacherInfo(map);
    }

    /**
     * 删除课程教师
     * @param courseTeacher 课程教师信息
     * @return 是否成功删除
     */
    @PostMapping("/deleteById")
    @ApiOperation(value = "删除课程教师")
    public boolean deleteById(@RequestBody CourseTeacher courseTeacher) {
        return courseTeacherService.deleteById(courseTeacher);
    }
}
