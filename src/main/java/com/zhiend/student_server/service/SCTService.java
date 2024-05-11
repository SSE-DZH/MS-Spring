package com.zhiend.student_server.service;

import com.zhiend.student_server.entity.CourseTeacherInfo;
import com.zhiend.student_server.entity.SCTInfo;
import com.zhiend.student_server.entity.StudentCourseTeacher;
import com.zhiend.student_server.mapper.StudentCourseTeacherMapper;
import com.zhiend.student_server.vo.CourseStatisticVO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
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
 * @Description: 选课管理服务
 * @Version 1.0.0
 */
@Service
public class SCTService {
    @Autowired
    private StudentCourseTeacherMapper studentCourseTeacherMapper;

    /**
     * 根据学生ID和学期查询选课信息
     * @param sid 学生ID
     * @param term 学期
     * @return 选课信息列表
     */
    public List<CourseTeacherInfo> findBySid(Integer sid, String term) {
        return studentCourseTeacherMapper.findByStudentId(sid, term);
    }

    /**
     * 查询所有学期
     * @return 学期列表
     */
    public List<String> findAllTerm() {
        return studentCourseTeacherMapper.findAllTerm();
    }

    /**
     * 检查选课是否存在
     * @param studentCourseTeacher 选课信息
     * @return 存在返回true，否则返回false
     */
    public boolean isSCTExist(StudentCourseTeacher studentCourseTeacher) {
        return studentCourseTeacherMapper.findBySCT(studentCourseTeacher).size() != 0;
    }

    /**
     * 保存选课信息
     * @param studentCourseTeacher 选课信息
     * @return 保存成功返回true，否则返回false
     */
    public boolean save(StudentCourseTeacher studentCourseTeacher) {
        return studentCourseTeacherMapper.insert(studentCourseTeacher);
    }

    /**
     * 根据选课信息删除选课记录
     * @param studentCourseTeacher 选课信息
     * @return 删除成功返回true，否则返回false
     */
    public boolean deleteBySCT(StudentCourseTeacher studentCourseTeacher) {
        return studentCourseTeacherMapper.deleteBySCT(studentCourseTeacher);
    }

    /**
     * 根据学生ID、课程ID、教师ID和学期删除选课记录
     * @param sid 学生ID
     * @param cid 课程ID
     * @param tid 教师ID
     * @param term 学期
     * @return 删除成功返回true，否则返回false
     */
    public boolean deleteById(Integer sid, Integer cid, Integer tid, String term) {
        StudentCourseTeacher studentCourseTeacher = new StudentCourseTeacher();
        studentCourseTeacher.setSid(sid);
        studentCourseTeacher.setCid(cid);
        studentCourseTeacher.setTid(tid);
        studentCourseTeacher.setTerm(term);
        return studentCourseTeacherMapper.deleteBySCT(studentCourseTeacher);
    }


    /**
     * 根据学生ID、课程ID、教师ID和学期查询选课信息
     * @param sid 学生ID
     * @param cid 课程ID
     * @param tid 教师ID
     * @param term 学期
     * @return 选课信息
     */
    public SCTInfo findByIdWithTerm(Integer sid, Integer cid, Integer tid, String term) {
        return studentCourseTeacherMapper.findBySearch(
                sid, null, 0,
                cid, null, 0,
                tid, null, 0,
                null, null, term).get(0);
    }

    /**
     * 根据学生ID、课程ID、教师ID和学期更新成绩
     * @param sid 学生ID
     * @param cid 课程ID
     * @param tid 教师ID
     * @param term 学期
     * @param grade 成绩
     * @return 更新成功返回true，否则返回false
     */
    public boolean updateById(Integer sid, Integer cid, Integer tid, String term, Integer grade) {
        return studentCourseTeacherMapper.updateById(sid, cid, tid, term, grade);
    }

    /**
     * 根据条件查询选课信息
     * @param map 查询条件
     * @return 选课信息列表
     */
    public List<SCTInfo> findBySearch(Map<String, String> map) {
        Integer sid = null, cid = null, tid = null;
        String sname = null, cname = null, tname = null, term = null;
        Integer sFuzzy = null, cFuzzy = null, tFuzzy = null;
        Integer lowBound = null, highBound = null;
        String classification = null; // 添加分类查询条件

        if (map.containsKey("cid")) {
            try {
                cid = Integer.parseInt(map.get("cid"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("sid")) {
            try {
                sid = Integer.parseInt(map.get("sid"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("tid")) {
            try {
                tid = Integer.parseInt(map.get("tid"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("sname")) {
            sname = map.get("sname");
        }
        if (map.containsKey("tname")) {
            tname = map.get("tname");
        }
        if (map.containsKey("cname")) {
            cname = map.get("cname");
        }
        if (map.containsKey("term")) {
            term = map.get("term");
        }
        if (map.containsKey("sFuzzy")) {
            sFuzzy = map.get("sFuzzy").equals("true") ? 1 : 0;
        }
        if (map.containsKey("tFuzzy")) {
            tFuzzy = map.get("tFuzzy").equals("true") ? 1 : 0;
        }
        if (map.containsKey("cFuzzy")) {
            cFuzzy = map.get("cFuzzy").equals("true") ? 1 : 0;
        }
        if (map.containsKey("lowBound")) {
            try {
                lowBound = Integer.parseInt(map.get("lowBound"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("highBound")) {
            try {
                highBound = Integer.valueOf(map.get("highBound"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("classification")) { // 解析分类查询条件
            classification = map.get("classification");
            System.out.println(classification);
        }

        System.out.println("SCT 查询：" + map);
        return studentCourseTeacherMapper.findBySearch1(
                sid, sname, sFuzzy,
                cid, cname, cFuzzy,
                tid, tname, tFuzzy,
                lowBound, highBound, term, classification);
    }

    /**
     * 根据课程名称和学期查找课程统计信息。
     *
     * @param cname 课程名称，用于查找特定课程的统计信息。
     * @param term 学期，指定查找的学期范围。
     * @return CourseStatisticVO 返回课程统计信息的视图对象，包含指定课程在指定学期的统计详情。
     */
    public CourseStatisticVO findByCname(String cname, String term) {
        // 初始化每个成绩范围的计数器
        int lessThan60 = 0;
        int sixtyTo69 = 0;
        int seventyTo79 = 0;
        int eightyTo89 = 0;
        int ninetyTo100 = 0;

        // 查询数据库以获取指定课程和学期的成绩
        List<Float> grades = studentCourseTeacherMapper.findGradesByCnameAndTerm(cname, term);

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

    /**
     * 导出课程业务数据为Excel表格。
     *
     * @param cname 课程名称
     * @param term 学期
     * @param response HttpServlet响应，用于向客户端提供下载
     */
    public void exportBusinessData(String cname, String term, HttpServletResponse response) {
        CourseStatisticVO courseStatisticVO = this.findByCname(cname, term);
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("templates/课程成绩报表.xlsx");
        try{
            //基于提供好的模板文件创建一个新的Excel表格对象
            XSSFWorkbook excel = new XSSFWorkbook(inputStream);
            //获得Excel文件中的一个Sheet页
            XSSFSheet sheet = excel.getSheet("Sheet1");

            //获得第1行
            XSSFRow row = sheet.getRow(0);
            //根据row将第一行第二列写入cname数据
            row.getCell(1).setCellValue(cname + "-成绩报表" +"（" + term + "）");

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

    public void export(Integer sid, String term, HttpServletResponse response) {
        List<CourseTeacherInfo> courseTeacherInfos = this.findBySid(sid, term);
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("templates/学生成绩报表.xlsx");
        try{
            //基于提供好的模板文件创建一个新的Excel表格对象
            XSSFWorkbook excel = new XSSFWorkbook(inputStream);
            //获得Excel文件中的一个Sheet页
            XSSFSheet sheet = excel.getSheet("Sheet1");

            //获得第1行
            XSSFRow row = sheet.getRow(0);
            //根据row将第一行第二列写入cname数据
            row.getCell(1).setCellValue(sid + "-成绩报表" +"（" + term + "）");

            //遍历courseTeacherInfos，从第3行开始写，每行都相邻，不需要隔开，向每行的第2列写入tname+ ":" + cname + ":" + grade
            for (int i = 0; i < courseTeacherInfos.size(); i++) {
                row = sheet.getRow(i + 2);
                row.getCell(1).setCellValue(courseTeacherInfos.get(i).getTname() + ":" + courseTeacherInfos.get(i).getCname() + ":" + courseTeacherInfos.get(i).getGrade());
            }
            // 紧接着下一行写入当前日期，精确到分秒，保留原有单元格样式
            row = sheet.getRow(courseTeacherInfos.size() + 2);
            Cell dateCell = row.getCell(1);
            dateCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            // 获取原有单元格样式
            XSSFCellStyle dateCellStyle = (XSSFCellStyle) dateCell.getCellStyle();
            // 创建一个新的单元格样式，并将原有样式属性复制到新的样式中
            XSSFCellStyle newCellStyle = excel.createCellStyle();
            newCellStyle.cloneStyleFrom(dateCellStyle);
            // 设置单元格文本水平对齐方式为右对齐
            newCellStyle.setAlignment(HorizontalAlignment.RIGHT);
            // 应用新的样式到单元格
            dateCell.setCellStyle(newCellStyle);





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
