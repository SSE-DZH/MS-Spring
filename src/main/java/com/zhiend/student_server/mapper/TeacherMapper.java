package com.zhiend.student_server.mapper;

import com.zhiend.student_server.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 教师数据访问层
 * @Auther: zhiend
 * @Date: 2024/4/9
 * @Description: 教师数据访问层
 * @Version 1.0.0
 */

@Repository
@Mapper
public interface TeacherMapper {
    /**
     * 查询所有教师信息
     * @return 教师信息列表
     */
    public List<Teacher> findAll();


    /**
     * 根据教师ID查询教师信息
     * @param tid 教师ID
     * @return 对应教师信息
     */
    public Teacher findById(@Param("tid") Integer tid);

    /**
     * 根据条件查询教师信息
     * @param tid 教师ID
     * @param tname 教师姓名
     * @param fuzzy 模糊查询标志
     * @return 符合条件的教师列表
     */
    public List<Teacher> findBySearch(@Param("tid") Integer tid, @Param("tname") String tname, @Param("fuzzy") Integer fuzzy);

    /**
     * 更新教师信息
     * @param teacher 教师对象
     * @return 更新结果，成功为true，失败为false
     */
    public boolean updateById(@Param("teacher") Teacher teacher);

    /**
     * 添加教师信息
     * @param teacher 教师对象
     * @return 添加结果，成功为true，失败为false
     */
    public boolean save(@Param("teacher") Teacher teacher);

    /**
     * 根据教师ID删除教师信息
     * @param tid 教师ID
     * @return 删除结果，成功为true，失败为false
     */
    public boolean deleteById(@Param("tid") Integer tid);
}
