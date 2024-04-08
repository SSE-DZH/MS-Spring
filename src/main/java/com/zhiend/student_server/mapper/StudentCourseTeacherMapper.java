package com.zhiend.student_server.mapper;

import com.zhiend.student_server.entity.CourseTeacherInfo;
import com.zhiend.student_server.entity.SCTInfo;
import com.zhiend.student_server.entity.StudentCourseTeacher;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Auther: zhiend
 * @Date: 2024/04/08
 * @Description: 学生选课关系映射器
 * @Version 1.0.0
 */

@Repository
@Mapper
public interface StudentCourseTeacherMapper {

    /**
     * 根据学生ID和学期查询选课信息
     * @param sid 学生ID
     * @param term 学期
     * @return 选课信息列表
     */
    public List<CourseTeacherInfo> findByStudentId(@Param("sid") Integer sid,
                                                   @Param("term") String term);

    /**
     * 根据条件查询选课信息
     * @param sid 学生ID
     * @param sname 学生姓名
     * @param sFuzzy 是否模糊查询学生姓名
     * @param cid 课程ID
     * @param cname 课程名称
     * @param cFuzzy 是否模糊查询课程名称
     * @param tid 教师ID
     * @param tname 教师姓名
     * @param tFuzzy 是否模糊查询教师姓名
     * @param lowBound 成绩下限
     * @param highBound 成绩上限
     * @param term 学期
     * @return 选课信息列表
     */
    public List<SCTInfo> findBySearch(@Param("sid") Integer sid,
                                      @Param("sname") String sname,
                                      @Param("sFuzzy") Integer sFuzzy,
                                      @Param("cid") Integer cid,
                                      @Param("cname") String cname,
                                      @Param("cFuzzy") Integer cFuzzy,
                                      @Param("tid") Integer tid,
                                      @Param("tname") String tname,
                                      @Param("tFuzzy") Integer tFuzzy,
                                      @Param("lowBound") Integer lowBound,
                                      @Param("highBound") Integer highBound,
                                      @Param("term") String term);

    /**
     * 查询所有学期
     * @return 所有学期列表
     */
    @Select("SELECT DISTINCT sct.term FROM studentms.sct sct")
    public List<String> findAllTerm();

    /**
     * 根据选课信息查询选课记录
     * @param studentCourseTeacher 选课信息对象
     * @return 选课记录列表
     */
    @Select("SELECT * FROM studentms.sct WHERE sid = #{sct.sid} AND cid = #{sct.cid} AND tid = #{sct.tid} AND term = #{sct.term}")
    public List<StudentCourseTeacher> findBySCT(@Param("sct") StudentCourseTeacher studentCourseTeacher);

    /**
     * 插入选课记录
     * @param studentCourseTeacher 选课信息对象
     * @return 是否插入成功
     */
    @Insert("INSERT INTO studentms.sct (sid, cid, tid, term) VALUES (#{s.sid}, #{s.cid}, #{s.tid}, #{s.term})")
    public boolean insert(@Param("s")StudentCourseTeacher studentCourseTeacher);

    /**
     * 根据学生ID、课程ID、教师ID和学期更新成绩
     * @param sid 学生ID
     * @param cid 课程ID
     * @param tid 教师ID
     * @param term 学期
     * @param grade 成绩
     * @return 是否更新成功
     */
    @Update("UPDATE studentms.sct SET sct.grade = #{grade} WHERE sct.sid = #{sid} AND sct.tid = #{tid} AND sct.cid = #{cid} AND sct.term = #{term}")
    public boolean updateById(@Param("sid") Integer sid,
                              @Param("cid") Integer cid,
                              @Param("tid") Integer tid,
                              @Param("term") String term,
                              @Param("grade") Integer grade);

    /**
     * 根据选课信息删除选课记录
     * @param sct 选课信息对象
     * @return 是否删除成功
     */
    @Delete("DELETE FROM studentms.sct WHERE sid = #{sct.sid} AND tid = #{sct.tid} AND cid = #{sct.cid}")
    public boolean deleteBySCT(@Param("sct") StudentCourseTeacher sct);
}
