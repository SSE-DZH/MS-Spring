package com.zhiend.student_server.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class StudentDTO implements Serializable {
    @NotBlank(message = "姓名不能为空")
    private String sname;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "1[3456789]\\d{9}", message = "手机号格式不正确")
    private String phone;
}
