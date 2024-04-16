package com.zhiend.student_server.controller;

import com.zhiend.student_server.entity.Course;
import com.zhiend.student_server.entity.CourseTeacher;
import com.zhiend.student_server.entity.CourseTeacherInfo;
import com.zhiend.student_server.result.Result;
import com.zhiend.student_server.service.CourseTeacherService;
import com.zhiend.student_server.vo.CourseStatisticVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
     * 导出运营数据报表
     * @param response
     */
    @GetMapping("/export/{tid}/{cname}/{term}")
    @ApiOperation("导出课程成绩报表")
    public Result export(@PathVariable Integer tid, @PathVariable String cname, @PathVariable String term, HttpServletResponse response){
        courseTeacherService.exportBusinessData(tid, cname, term, response);
        return Result.success();
    }

    /**
     * 根据课程名字和学期返回课程统计信息。
     *
     * @param cname 课程名称，作为路径变量传递。
     * @param term 学期，作为路径变量传递。
     * @return 返回一个Result对象，其中包含CourseStatisticVO类型的课程统计信息。如果查询成功，Result的success方法将返回统计信息；否则，返回相关错误信息。
     */
    @PostMapping("/findByCname/{tid}/{cname}/{term}")
    @ApiOperation("根据课程名字返回CourseStatisticVO")
    public Result<CourseStatisticVO> findByCname(@PathVariable Integer tid, @PathVariable String cname, @PathVariable String term) {
        // 调用sctService中的findByCname方法查询课程统计信息，并将结果封装在Result对象中返回
        return Result.success(courseTeacherService.findByCname(tid, cname, term));
    }

    /**
     * 根据教师ID查询该教师的所有课程名称。
     *
     * @param tid 教师ID，路径变量，用于指定要查询课程的教师。
     * @return 返回一个课程名称的列表，对应于指定教师的所有课程。
     */
    @GetMapping("/findMyCourse/{tid}")
    @ApiOperation(value = "根据tid查询所有课程")
    public List<String> findMyCourseName(@PathVariable Integer tid) {
        // 调用服务层方法，根据tid查询教师的所有课程名称
        return courseTeacherService.findMyCourseName(tid);
    }

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
