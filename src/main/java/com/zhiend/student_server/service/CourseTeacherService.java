package com.zhiend.student_server.service;

import com.zhiend.student_server.entity.Course;
import com.zhiend.student_server.entity.CourseTeacher;
import com.zhiend.student_server.entity.CourseTeacherInfo;
import com.zhiend.student_server.mapper.CourseTeacherMapper;
import com.zhiend.student_server.vo.CourseStatisticVO;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zhiend
 * @Date: 2024/04/08
 * @Description: 课程教师服务类
 * @Version 1.0.0
 */

@Service
public class CourseTeacherService {
    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    /**
     * 插入课程教师信息
     * @param cid 课程ID
     * @param tid 教师ID
     * @param term 学期
     * @return 是否成功插入
     */
    public boolean insertCourseTeacher(Integer cid,
                                       Integer tid,
                                       String term,
                                       String location,
                                       String schedule) {
        return courseTeacherMapper.insertCourseTeacher(cid, tid, term, location, schedule);
    }


    /**
     * 查询教师开设的课程
     * @param tid 教师ID
     * @param term 学期
     * @return 教师开设的课程列表
     */
    public List<Course> findMyCourse(Integer tid, String term) {
        return courseTeacherMapper.findMyCourse(tid, term);
    }

    /**
     * 根据条件查询课程教师信息
     * @param map 查询条件
     * @return 符合条件的课程教师信息列表
     */
    public List<CourseTeacherInfo> findCourseTeacherInfo(Map<String, String> map) {
        Integer tid = null, cid = null;
        Integer tFuzzy = null, cFuzzy = null;
        String tname = null, cname = null;
        if (map.containsKey("tid")) {
            try {
                tid = Integer.parseInt(map.get("tid"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("cid")) {
            try {
                cid = Integer.parseInt(map.get("cid"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("tname")) {
            tname = map.get("tname");
        }
        if (map.containsKey("cname")) {
            cname = map.get("cname");
        }
        if (map.containsKey("tFuzzy")) {
            tFuzzy = (map.get("tFuzzy").equals("true")) ? 1 : 0;
        }
        if (map.containsKey("cFuzzy")) {
            cFuzzy = (map.get("cFuzzy").equals("true")) ? 1 : 0;
        }
        System.out.println("课程教师模糊查询：" + map);
        System.out.println(courseTeacherMapper.findCourseTeacherInfo(tid, tname, tFuzzy, cid, cname, cFuzzy));
        return courseTeacherMapper.findCourseTeacherInfo(tid, tname, tFuzzy, cid, cname, cFuzzy);
    }

    /**
     * 根据条件查询课程教师信息
     * @param cid 课程ID
     * @param tid 教师ID
     * @param term 学期
     * @return 符合条件的课程教师信息列表
     */
    public List<CourseTeacher> findBySearch(Integer cid, Integer tid, String term) {
        return courseTeacherMapper.findBySearch(cid, tid, term);
    }

    /**
     * 根据条件查询课程教师信息
     * @param map 查询条件
     * @return 符合条件的课程教师信息列表
     */
    public List<CourseTeacher> findBySearch(Map<String, String> map) {
        Integer cid = null;
        Integer tid = null;
        String  term = null;

        if (map.containsKey("term")) {
            term = map.get("term");
        }

        if (map.containsKey("tid")) {
            try {
                tid = Integer.parseInt(map.get("tid"));
            }
            catch (Exception e) {
            }
        }

        if (map.containsKey("cid")) {
            try {
                cid = Integer.parseInt(map.get("cid"));
            }
            catch (Exception e) {
            }
        }
        System.out.println("开课表查询：" + map);
        return courseTeacherMapper.findBySearch(cid, tid, term);
    }

    /**
     * 删除课程教师信息
     * @param courseTeacher 课程教师信息
     * @return 是否成功删除
     */
    public boolean deleteById(CourseTeacher courseTeacher) {
        return courseTeacherMapper.deleteById1(courseTeacher);
    }


    /**
     * 根据教师ID查找该教师所教授的课程名称列表。
     *
     * @param tid 教师的唯一标识符，类型为Integer。
     * @return 返回一个List<String>类型的结果，包含该教师的所有课程名称。
     */
    public List<String> findMyCourseName(Integer tid) {
        // 通过课程教师Mapper查找指定教师ID的课程名称列表
        return courseTeacherMapper.findCourseNamesByTid(tid);
    }

    /**
     * 根据课程名称和学期查找课程统计信息。
     *
     * @param tid
     * @param cname 课程名称，用于查找特定课程的统计信息。
     * @param term  学期，指定查找的学期范围。
     * @return CourseStatisticVO 返回课程统计信息的视图对象，包含指定课程在指定学期的统计详情。
     */
    public CourseStatisticVO findByCname(Integer tid, String cname, String term) {
        // 初始化每个成绩范围的计数器
        int lessThan60 = 0;
        int sixtyTo69 = 0;
        int seventyTo79 = 0;
        int eightyTo89 = 0;
        int ninetyTo100 = 0;

        // 查询数据库以获取指定课程和学期的成绩
        List<Float> grades = courseTeacherMapper.findGradesByCnameAndTerm(tid, cname, term);

        // 遍历成绩并在相应范围内进行计数
        for (Float grade : grades) {
            if (grade < 60) {
                lessThan60++;
            } else if (grade >= 60 && grade < 70) {
                sixtyTo69++;
            } else if (grade >= 70 && grade < 80) {
                seventyTo79++;
            } else if (grade >= 80 && grade < 90) {
                eightyTo89++;
            } else if (grade >= 90 && grade <= 100) {
                ninetyTo100++;
            }
        }

        // 创建并返回 CourseStatisticVO 对象，其中包含计数的成绩范围
        return new CourseStatisticVO(lessThan60, sixtyTo69, seventyTo79, eightyTo89, ninetyTo100);
    }

    public void exportBusinessData(Integer tid, String cname, String term, HttpServletResponse response) {
        CourseStatisticVO courseStatisticVO = this.findByCname(tid, cname, term);
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("templates/课程成绩报表.xlsx");
        try{
            //基于提供好的模板文件创建一个新的Excel表格对象
            XSSFWorkbook excel = new XSSFWorkbook(inputStream);
            //获得Excel文件中的一个Sheet页
            XSSFSheet sheet = excel.getSheet("Sheet1");

            //获得第1行
            XSSFRow row = sheet.getRow(0);
            //根据row将第一行第二列写入cname数据
            row.getCell(1).setCellValue(cname + "成绩报表" +"（" + term + "）");

            //获得第4行
            row = sheet.getRow(3);
            row.getCell(1).setCellValue(courseStatisticVO.getLessThan60() + "人");

            //获得第6行
            row = sheet.getRow(5);
            row.getCell(1).setCellValue(courseStatisticVO.getSixtyTo69() + "人");

            //获得第8行
            row = sheet.getRow(7);
            row.getCell(1).setCellValue(courseStatisticVO.getSeventyTo79() + "人");

            //获得第10行
            row = sheet.getRow(9);
            row.getCell(1).setCellValue(courseStatisticVO.getEightyTo89() + "人");

            //获得第12行
            row = sheet.getRow(11);
            row.getCell(1).setCellValue(courseStatisticVO.getNinetyTo100() + "人");

            //获得第13行
            row = sheet.getRow(12);
            //写入当前日期，精确到分秒
            row.getCell(1).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            //通过输出流将文件下载到客户端浏览器中
            ServletOutputStream out = response.getOutputStream();
            excel.write(out);
            //关闭资源
            out.flush();
            out.close();
            excel.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
