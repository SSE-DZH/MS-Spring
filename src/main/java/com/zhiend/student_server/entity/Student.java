package com.zhiend.student_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * @Auther: zhiend
 * @Date: 2024/04/08
 * @Description: 学生实体类
 * @Version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("Student")
public class Student {
    private Integer sid;        // 学生编号
    private String sname;       // 学生姓名
    private String password;    // 密码
    private String avatar;      // 头像
    private String email;       // 邮箱
    private String phone;       // 手机号
}
