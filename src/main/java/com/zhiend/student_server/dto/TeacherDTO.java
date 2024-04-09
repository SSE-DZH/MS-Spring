package com.zhiend.student_server.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class TeacherDTO {
    @NotBlank(message = "教师姓名不能为空")
    private String tname;       // 教师姓名

    @NotBlank(message = "密码不能为空")
    private String password;    // 密码

    @NotBlank(message = "邮箱不能为空")
    private String email;       // 邮箱

    @NotBlank(message = "手机号不能为空")
    private String phone;       // 手机号
}
