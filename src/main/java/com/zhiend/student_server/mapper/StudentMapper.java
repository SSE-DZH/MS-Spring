package com.zhiend.student_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhiend.student_server.dto.AvatarDTO;
import com.zhiend.student_server.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 学生数据访问接口
 * @Auther: zhiend
 * @Date: 2024/4/8
 * @Description: 学生数据访问接口
 * @Version 1.0.0
 */

@Mapper
@Repository
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 查询所有学生信息
     * @return 学生信息列表
     */
    public List<Student> findAll();

    @Select("SELECT sid FROM studentms.student WHERE student.sname = #{username}")
    Integer findIdByUsername(String username);

    /**
     * 根据学生ID查询学生信息
     * @param sid 学生ID
     * @return 学生对象
     */
    public Student findById(@Param("sid") Integer sid);

    /**
     * 根据条件查询学生信息
     * @param student 学生对象，包含查询条件
     * @param fuzzy 是否模糊查询，1表示模糊查询，0表示精确查询
     * @return 符合条件的学生列表
     */
    public List<Student> findBySearch(@Param("student") Student student, @Param("fuzzy") Integer fuzzy);

    /**
     * 更新学生信息
     * @param student 待更新的学生对象
     * @return 更新成功与否
     */
    public boolean updateById1(@Param("student") Student student);

    /**
     * 添加学生信息
     * @param student 待添加的学生对象
     * @return 添加成功与否
     */
    public boolean save(@Param("student") Student student);

    /**
     * 根据学生ID删除学生信息
     * @param sid 学生ID
     * @return 删除成功与否
     */
    public boolean deleteById(@Param("sid") Integer sid);

    /**
     * 根据用户名查询学生信息
     * @param username 用户名
     * @return 学生对象
     */
    @Select("SELECT * FROM studentms.student WHERE sname = #{username}")
    Student findByUsername(String username);

    /**
     * 更新学生头像
     * @param avatarDTO 头像信息
     * @return 更新成功与否
     */
    @Update("UPDATE studentms.student SET student.avatar = #{avatarDTO.avatar} WHERE student.sname = #{avatarDTO.username}")
    boolean updateAvatar(@Param("avatarDTO") AvatarDTO avatarDTO);
}
