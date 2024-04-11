package com.zhiend.student_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for updating user password.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDTO {
    
    /**
     * User's username.
     */
    private String username;
    
    /**
     * User's new password.
     */
    private String password;

    /**
     * User's verification code.
     */
    private String verificationCode;
}
