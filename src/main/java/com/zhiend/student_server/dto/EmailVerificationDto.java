package com.zhiend.student_server.dto;

import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 邮箱验证码传输对象
 */
@NoArgsConstructor
public class EmailVerificationDto {

    // 邮箱字段，使用@Email注解进行校验，确保输入的值符合电子邮件格式
    @Email(message = "Invalid email format")
    private String email;

    // 验证码字段，使用@NotBlank注解进行校验，确保输入的值不为空且非仅有空白字符
    @NotBlank(message = "Verification code is required")
    private String verificationCode;

    // 构造函数（可选，根据实际需要决定是否添加）
    public EmailVerificationDto(String email, String verificationCode) {
        this.email = email;
        this.verificationCode = verificationCode;
    }

    // getter和setter方法

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
