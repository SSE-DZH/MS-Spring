package com.zhiend.student_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * @Auther: zhiend
 * @Date: 2024/04/08
 * @Description: 学生-课程-教师关联实体类
 * @Version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("StudentCourseTeacher")
public class StudentCourseTeacher {
    private Integer sctid;      // 学生-课程-教师关联编号
    private Integer sid;        // 学生编号
    private Integer cid;        // 课程编号
    private Integer tid;        // 教师编号
    private Float grade;        // 成绩
    private String term;        // 学期
}
