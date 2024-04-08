package com.zhiend.student_server.mapper;

import com.zhiend.student_server.entity.Course;
import com.zhiend.student_server.entity.CourseTeacher;
import com.zhiend.student_server.entity.CourseTeacherInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
public interface CourseTeacherMapper {

    /**
     * 插入课程教师信息
     * @param cid 课程ID
     * @param tid 教师ID
     * @param term 学期
     * @return 是否成功插入
     */
    @Insert("INSERT INTO studentms.ct (cid, tid, term) VALUES (#{cid}, #{tid}, #{term})")
    public boolean insertCourseTeacher(@Param("cid") Integer cid,
                                       @Param("tid") Integer tid,
                                       @Param("term") String term);

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
    @Delete("DELETE FROM studentms.ct WHERE cid = #{c.cid} AND tid = #{c.tid}")
    public boolean deleteById(@Param("c") CourseTeacher courseTeacher);
}
