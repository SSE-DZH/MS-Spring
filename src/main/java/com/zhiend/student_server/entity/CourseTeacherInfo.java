package com.zhiend.student_server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * @Auther: zhiend
 * @Date: 2024/04/08
 * @Description: 教师课程信息实体类
 * @Version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("CourseTeacherInfo")
public class CourseTeacherInfo {
    private Integer cid;        // 课程编号
    private Integer tid;        // 教师编号
    private String cname;       // 课程名
    private String tname;       // 教师名
    private Integer ccredit;    // 学分
    private Float grade;        // 成绩
    private String location;    // 地点
    private String schedule;    // 时间表
}
