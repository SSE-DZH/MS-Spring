package com.zhiend.student_server.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Classname LoginVO
 * @Description 登录返回的数据格式
 * @Date 2024/4/17 14:46
 * @Created by Zhiend
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "登录返回的数据格式")
public class LoginVO implements Serializable {

    @ApiModelProperty("jwt令牌")
    private String token;
}
