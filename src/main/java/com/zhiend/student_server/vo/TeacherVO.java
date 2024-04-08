package com.zhiend.student_server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * @Auther: zhiend
 * @Date: 2024/04/08
 * @Description: 教师值对象类
 * @Version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("TeacherVO")
public class TeacherVO {
    private Integer tid;        // 教师编号
    private String tname;       // 教师姓名
    private String password;    // 密码
}
