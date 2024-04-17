package com.zhiend.student_server.controller;

import com.zhiend.student_server.entity.CourseTeacherInfo;
import com.zhiend.student_server.entity.SCTInfo;
import com.zhiend.student_server.entity.StudentCourseTeacher;
import com.zhiend.student_server.result.Result;
import com.zhiend.student_server.service.SCTService;
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
 * @Description: 选课管理控制器
 * @Version 1.0.0
 */

@RestController
@CrossOrigin("*")
@RequestMapping("/SCT")
@Api(tags = "选课管理")
public class SCTController {
    @Autowired
    private SCTService sctService;

    /**
     * 根据学生ID（sid）和学期（term）导出成绩报表。
     * <p>
     * 该接口使用GET请求，路径格式为/export/{sid}/{term}，其中{sid}为学生ID，{term}为学期。
     * 调用该接口会触发成绩报表的导出，并将生成的报表以响应（response）的形式返回。
     * </p>
     * @param sid 学生ID，路径参数，用于指定要导出成绩的学生。
     * @param term 学期，路径参数，用于指定要导出成绩的学期。
     * @param response HttpServletResponse，用于将生成的成绩报表作为响应返回给客户端。
     * @return Result 成功导出的提示结果，通常为一个包含成功状态和消息的对象。
     */
    @GetMapping("/exportStudent/{sid}/{term}")
    @ApiOperation("导出成绩报表")
    public Result export(@PathVariable Integer sid, @PathVariable String term, HttpServletResponse response) {
        // 调用服务层方法，实际执行成绩报表的导出
        sctService.export(sid, term, response);
        // 返回成功结果，表示成绩报表已成功导出
        return Result.success();
    }

    /**
     * 导出admin运营数据报表
     * @param response
     */
    @GetMapping("/export/{cname}/{term}")
    @ApiOperation("导出课程成绩报表")
    public Result export(@PathVariable String cname, @PathVariable String term, HttpServletResponse response){
        sctService.exportBusinessData(cname, term, response);
        return Result.success();
    }

    /**
     * 根据课程名字和学期返回课程统计信息。
     *
     * @param cname 课程名称，作为路径变量传递。
     * @param term 学期，作为路径变量传递。
     * @return 返回一个Result对象，其中包含CourseStatisticVO类型的课程统计信息。如果查询成功，Result的success方法将返回统计信息；否则，返回相关错误信息。
     */
    @PostMapping("/findByCname/{cname}/{term}")
    @ApiOperation("根据课程名字返回CourseStatisticVO")
    public Result<CourseStatisticVO> findByCname(@PathVariable String cname, @PathVariable String term) {
        // 调用sctService中的findByCname方法查询课程统计信息，并将结果封装在Result对象中返回
        return Result.success(sctService.findByCname(cname, term));
    }

    @PostMapping("/save")
    @ApiOperation("选课")
    public String save(@RequestBody StudentCourseTeacher studentCourseTeacher) {
        if (sctService.isSCTExist(studentCourseTeacher)) {
            return "禁止重复选课";
        }
        System.out.println("正在保存 sct 记录：" + studentCourseTeacher);
        return sctService.save(studentCourseTeacher) ? "选课成功" : "选课失败，联系管理员";
    }

    @GetMapping("/findBySid/{sid}/{term}")
    @ApiOperation("根据学生ID和学期查询选课信息")
    public List<CourseTeacherInfo> findBySid(@PathVariable Integer sid, @PathVariable String term) {
        return sctService.findBySid(sid, term);
    }

    @GetMapping("/findAllTerm")
    @ApiOperation("查询所有学期")
    public List<String> findAllTerm() {
        return sctService.findAllTerm();
    }

    @PostMapping("/deleteBySCT")
    @ApiOperation("根据选课信息删除选课记录")
    public boolean deleteBySCT(@RequestBody StudentCourseTeacher studentCourseTeacher) {
        System.out.println("正在删除 sct 记录：" + studentCourseTeacher);
        return sctService.deleteBySCT(studentCourseTeacher);
    }

    @PostMapping("/findBySearch")
    @ApiOperation("根据条件查询选课信息")
    public List<SCTInfo> findBySearch(@RequestBody Map<String, String> map) {
        return sctService.findBySearch(map);
    }

    @GetMapping("/findById/{sid}/{cid}/{tid}/{term}")
    @ApiOperation("根据学生ID、课程ID、教师ID和学期查询选课信息")
    public SCTInfo findById(@PathVariable Integer sid,
                            @PathVariable Integer cid,
                            @PathVariable Integer tid,
                            @PathVariable String term) {
        return sctService.findByIdWithTerm(sid, cid, tid, term);
    }

    @GetMapping("/updateById/{sid}/{cid}/{tid}/{term}/{grade}")
    @ApiOperation("根据学生ID、课程ID、教师ID和学期更新成绩")
    public boolean updateById(@PathVariable Integer sid,
                              @PathVariable Integer cid,
                              @PathVariable Integer tid,
                              @PathVariable String term,
                              @PathVariable Integer grade) {
        return sctService.updateById(sid, cid, tid, term, grade);
    }

    @GetMapping("/deleteById/{sid}/{cid}/{tid}/{term}")
    @ApiOperation("根据学生ID、课程ID、教师ID和学期删除选课记录")
    public boolean deleteById(@PathVariable Integer sid,
                              @PathVariable Integer cid,
                              @PathVariable Integer tid,
                              @PathVariable String term) {
        return sctService.deleteById(sid, cid, tid, term);
    }
}
