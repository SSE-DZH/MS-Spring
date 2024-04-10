package com.zhiend.student_server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * @Auther: zhiend
 * @Date: 2024/04/08
 * @Description: 教师课程关联实体类
 * @Version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("CourseTeacher")
public class CourseTeacher {
    @TableId
    private Integer ctid;      // 关联编号
    private Integer cid;       // 课程编号
    private Integer tid;       // 教师编号
    private String term;       // 学期
    private String location;   // 地点
    private String schedule;   // 时间表
}
