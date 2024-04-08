package com.zhiend.student_server.service;

import com.zhiend.student_server.entity.Student;
import com.zhiend.student_server.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生业务逻辑处理类
 * @Auther: zhiend
 * @Date: 2024/4/8
 * @Description: 学生信息管理服务
 * @Version 1.0.0
 */
@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 根据页码和每页大小查询学生列表
     * @param num 页码，从零开始
     * @param size 每页大小
     * @return 当页学生列表
     */
    public List<Student> findByPage(Integer num, Integer size) {
        List<Student> studentList = studentMapper.findAll();
        ArrayList<Student> list = new ArrayList<Student>();

        int start = size * num;
        int end = size * (num + 1);
        int sz = studentList.size();

        for (int i = start; i < end && i < sz; i++) {
            list.add(studentList.get(i));
        }

        return list;
    }

    /**
     * 根据条件查询学生信息
     * @param sid 学生ID
     * @param sname 学生姓名
     * @param fuzzy 是否模糊查询，1表示模糊查询，0表示精确查询
     * @return 符合条件的学生列表
     */
    public List<Student> findBySearch(Integer sid, String sname, Integer fuzzy) {
        Student student = new Student();
        student.setSid(sid);
        student.setSname(sname);
        fuzzy = (fuzzy == null) ? 0 : fuzzy;

        return studentMapper.findBySearch(student, fuzzy);
    }

    /**
     * 获取学生总数
     * @return 学生总数
     */
    public Integer getLength() {
        return studentMapper.findAll().size();
    }

    /**
     * 根据学生ID查询学生信息
     * @param sid 学生ID
     * @return 学生对象
     */
    public Student findById(Integer sid) {
        return studentMapper.findById(sid);
    }

    /**
     * 更新学生信息
     * @param student 待更新的学生对象
     * @return 更新成功与否
     */
    public boolean updateById(Student student) {
        return studentMapper.updateById(student);
    }

    /**
     * 添加学生信息
     * @param student 待添加的学生对象
     * @return 添加成功与否
     */
    public boolean save(Student student) {
        return studentMapper.save(student);
    }

    /**
     * 根据学生ID删除学生信息
     * @param sid 学生ID
     * @return 删除成功与否
     */
    public boolean deleteById(Integer sid) {
        return studentMapper.deleteById(sid);
    }
}
