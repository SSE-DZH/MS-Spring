package com.zhiend.student_server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * @Auther: zhiend
 * @Date: 2024/04/08
 * @Description: 课程实体类
 * @Version 1.0.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("Course")
public class Course {
    @TableId
    private Integer cid;       // 课程编号
    private String cname;      // 课程名称
    private Integer ccredit;   // 学分
    private String location;   // 地点
    private String schedule;   // 时间表
}
