package com.zhiend.student_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

/**
 * @Auther: zhiend
 * @Date: 2024/04/08
 * @Description: 选课信息实体类
 * @Version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("SCTInfo")
@ToString
public class SCTInfo {
    private Integer sid;        // 学生编号
    private Integer tid;        // 教师编号
    private Integer cid;        // 课程编号
    private String sname;       // 学生姓名
    private String tname;       // 教师姓名
    private String cname;       // 课程名
    private Float grade;        // 成绩
    private String term;        // 学期
    private String location;    // 地点
    private String schedule;    // 时间表
}
