package com.zhiend.student_server.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: zhiend
 * @Date: 2024/04/08
 * @Description: 信息控制器
 * @Version 1.0.0
 */

@RestController
@RequestMapping("/info")
@CrossOrigin("*")
public class InfoController {
    private final String CURRENT_TERM = "24-春季学期";
    private final boolean FORBID_COURSE_SELECTION = false;

    /**
     * 获取当前学期信息
     * @return 当前学期
     */
    @RequestMapping("/getCurrentTerm")
    public String getCurrentTerm() {
        return CURRENT_TERM;
    }

    /**
     * 获取是否禁止选课信息
     * @return 是否禁止选课
     */
    @RequestMapping("/getForbidCourseSelection")
    public boolean getForbidCourseSelection() {
        return FORBID_COURSE_SELECTION;
    }
}
