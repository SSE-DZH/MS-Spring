package com.zhiend.student_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhiend.student_server.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zhiend
 * @Date: 2024/04/08
 * @Description: 课程Mapper接口
 * @Version: 1.0.0
 */
@Repository
@Mapper
public interface CourseMapper extends BaseMapper {
    /**
     * 根据条件查询课程
     *
     * @param cid       课程ID
     * @param cname     课程名称
     * @param fuzzy     是否模糊搜索，1表示是，0表示否
     * @param lowBound  分数下限
     * @param highBound 分数上限
     * @return 符合条件的课程列表
     */
    List<Course> findBySearch(@Param("cid") Integer cid,
                              @Param("cname") String cname,
                              @Param("fuzzy") Integer fuzzy,
                              @Param("lowBound") Integer lowBound,
                              @Param("highBound") Integer highBound);

    /**
     * 插入新课程
     *
     * @param course 待插入的课程对象
     * @return 插入是否成功
     */
    boolean insertCourse(Course course);

    /**
     * 根据课程ID更新课程信息
     *
     * @param course 待更新的课程对象
     * @return 更新是否成功
     */
    boolean updateById(@Param("course") Course course);

    /**
     * 根据课程ID删除课程
     *
     * @param cid 待删除课程的ID
     * @return 删除是否成功
     */
    boolean deleteById(@Param("cid") Integer cid);

    /**
     * 查询所有课程名称
     *
     * @return 所有课程名称的列表
     */
    @Select("SELECT cname FROM course")
    List<String> findAllCname();
}
