package com.zhiend.student_server.vo;// CourseStatisticVO.java

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 课程统计信息视图对象
 * 用于存储课程不同成绩段的学生人数统计
 */
@AllArgsConstructor
@NoArgsConstructor // 为类提供了无参数的构造函数
@Data // 为类生成getter、setter、toString、hashCode和equals方法
@ToString // 重写toString方法，便于输出对象信息
public class CourseStatisticVO {
    private int lessThan60; // 不及格（小于60分）的学生人数
    private int sixtyTo69; // 60到69分的学生人数
    private int seventyTo79; // 70到79分的学生人数
    private int eightyTo89; // 80到89分的学生人数
    private int ninetyTo100; // 90到100分的学生人数
}

