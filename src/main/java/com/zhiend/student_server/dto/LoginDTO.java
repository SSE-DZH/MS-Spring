package com.zhiend.student_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录传输对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {
    //用户名
    private String username;
    //密码
    private String password;
}