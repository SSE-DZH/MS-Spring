package com.zhiend.student_server.mapper;

import com.zhiend.student_server.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
public interface StudentMapper {

    /**
     * 查询所有学生信息
     * @return 学生信息列表
     */
    public List<Student> findAll();

    @Select("SELECT sid FROM studentms.Student WHERE student.sname = #{username}")
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
    public boolean updateById(@Param("student") Student student);

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

    @Select("SELECT * FROM studentms.Student WHERE sname = #{username}")
    Student findByUsername(String username);
}
