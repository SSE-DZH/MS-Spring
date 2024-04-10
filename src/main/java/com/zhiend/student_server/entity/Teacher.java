package com.zhiend.student_server.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * @Auther: zhiend
 * @Date: 2024/04/08
 * @Description: 教师实体类
 * @Version 1.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("Teacher")
public class Teacher {
    @TableId
    private Integer tid;        // 教师编号
    private String tname;       // 教师姓名
    private String password;    // 密码
    private String avatar;      // 头像
    private String email;       // 邮箱
    private String phone;       // 手机号
}
