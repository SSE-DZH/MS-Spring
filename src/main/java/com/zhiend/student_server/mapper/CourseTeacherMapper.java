package com.zhiend.student_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhiend.student_server.entity.Course;
import com.zhiend.student_server.entity.CourseTeacher;
import com.zhiend.student_server.entity.CourseTeacherInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: zhiend
 * @Date: 2024/04/08
 * @Description: 课程教师Mapper接口
 * @Version 1.0.0
 */
@Repository
@Mapper
public interface CourseTeacherMapper extends BaseMapper<CourseTeacher>{

    /**
     * 插入课程教师信息
     * @param cid 课程ID
     * @param tid 教师ID
     * @param term 学期
     * @return 是否成功插入
     */
    @Insert("INSERT INTO studentms.courseteacher (cid, tid, term, location, schedule) VALUES (#{cid}, #{tid}, #{term}, #{location}, #{schedule})")
    public boolean insertCourseTeacher(@Param("cid") Integer cid,
                                       @Param("tid") Integer tid,
                                       @Param("term") String term,
                                       @Param("location") String location,
                                       @Param("schedule") String schedule);

    /**
     * 根据条件查询课程教师信息
     * @param cid 课程ID
     * @param tid 教师ID
     * @param term 学期
     * @return 符合条件的课程教师信息列表
     */
    public List<CourseTeacher> findBySearch(@Param("cid") Integer cid,
                                            @Param("tid") Integer tid,
                                            @Param("term") String term);

    /**
     * 查询教师开设的课程
     * @param tid 教师ID
     * @param term 学期
     * @return 教师开设的课程列表
     */
    public List<Course> findMyCourse(@Param("tid") Integer tid,
                                     @Param("term") String term);

    /**
     * 根据条件查询课程教师信息
     * @param tid 教师ID
     * @param tname 教师姓名
     * @param tFuzzy 教师姓名是否模糊查询，1为是，0为否
     * @param cid 课程ID
     * @param cname 课程名
     * @param cFuzzy 课程名是否模糊查询，1为是，0为否
     * @return 符合条件的课程教师信息列表
     */
    public List<CourseTeacherInfo> findCourseTeacherInfo(@Param("tid") Integer tid,
                                                         @Param("tname") String tname,
                                                         @Param("tFuzzy") Integer tFuzzy,
                                                         @Param("cid") Integer cid,
                                                         @Param("cname") String cname,
                                                         @Param("cFuzzy") Integer cFuzzy);

    /**
     * 删除课程教师信息
     * @param courseTeacher 课程教师信息
     * @return 是否成功删除
     */
    @Delete("DELETE FROM studentms.courseteacher WHERE cid = #{c.cid} AND tid = #{c.tid}")
    public boolean deleteById1(@Param("c") CourseTeacher courseTeacher);

    /**
     * 根据教师ID查询其所教授的课程名称列表。
     *
     * 本方法通过动态构建的查询条件，根据指定的教师ID（tid），查询数据库中该教师所教授的所有课程的名称。
     * 查询过程中，会使用INNER JOIN将courseteacher表和course表关联起来，以便获取完整的课程名称信息。
     *
     * @param tid 教师ID，用于指定要查询的教师。
     * @return 返回一个字符串列表，列表中包含指定教师ID所教授的所有课程名称。
     */
    @Select("SELECT c.cname FROM courseteacher ct INNER JOIN course c ON ct.cid = c.cid WHERE ct.tid = #{tid}")
    List<String> findCourseNamesByTid(@Param("tid") Integer tid);

    @Select("SELECT sct.grade FROM studentcourseteacher sct " +
            "INNER JOIN course c ON sct.cid = c.cid " +
            "WHERE c.cname = #{cname} AND sct.term = #{term} AND sct.tid = #{tid}")
    List<Float> findGradesByCnameAndTerm(@Param("tid") Integer tid, @Param("cname") String cname, @Param("term") String term);

}
