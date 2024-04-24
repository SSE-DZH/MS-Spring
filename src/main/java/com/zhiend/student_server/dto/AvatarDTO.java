package com.zhiend.student_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Classname AvatarDTO
 * @Description 上传头像DTO
 * @Date 2024/4/24 12:45
 * @Created by Zhiend
 */
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvatarDTO {
    private String username;
    private String avatar;
}
