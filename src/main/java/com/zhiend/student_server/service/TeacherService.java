package com.zhiend.student_server.service;

import com.zhiend.student_server.entity.Student;
import com.zhiend.student_server.entity.Teacher;
import com.zhiend.student_server.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 教师服务
 * @Auther: zhiend
 * @Date: 2024/4/9
 * @Description: 教师服务
 * @Version 1.0.0
 */

@Service
@Api(tags = "教师服务")
public class TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 根据条件查询教师信息
     * @param map 查询条件，包含教师ID、姓名和模糊查询标志
     * @return 符合条件的教师列表
     */
    @ApiOperation("根据条件查询教师信息")
    public List<Teacher> findBySearch(Map<String, String> map) {
        Integer tid = null;
        String tname = null;
        Integer fuzzy = null;
        if (map.containsKey("tid")) {
            try {
                tid = Integer.parseInt(map.get("tid"));
            } catch (Exception e) {
            }
        }
        if (map.containsKey("tname")) {
            tname = map.get("tname");
        }
        if (map.containsKey("fuzzy")) {
            fuzzy = map.get("fuzzy").equals("true") ? 1 : 0;
        }
        System.out.println(map);
        System.out.println("查询类型：" + tid + ", " + tname + ", " + fuzzy);
        return teacherMapper.findBySearch(tid, tname, fuzzy);
    }

    /**
     * 根据用户名查询教师信息
     * @param username 用户名
     * @return 教师对象
     */
    public Teacher findByUsername(String username) {
        return teacherMapper.findByUsername(username);
    }


    /**
     * 分页查询教师信息
     * @param num 当前页数
     * @param size 每页大小
     * @return 分页后的教师列表
     */
    @ApiOperation("分页查询教师信息")
    public List<Teacher> findByPage(Integer num, Integer size) {
        List<Teacher> teacherList = teacherMapper.findAll();
        ArrayList<Teacher> list = new ArrayList<>();

        int start = size * num;
        int end = size * (num + 1);
        int sz = teacherList.size();

        for (int i = start; i < end && i < sz; i++) {
            list.add(teacherList.get(i));
        }

        return list;
    }

    /**
     * 获取教师总数
     * @return 教师总数
     */
    @ApiOperation("获取教师总数")
    public Integer getLength() {
        return teacherMapper.findAll().size();
    }

    /**
     * 根据教师ID查询教师信息
     * @param tid 教师ID
     * @return 对应教师信息
     */
    @ApiOperation("根据ID查询教师信息")
    public Teacher findById(Integer tid) {
        return teacherMapper.findById(tid);
    }

    /**
     * 更新教师信息
     * @param teacher 教师对象
     * @return 更新结果，成功为true，失败为false
     */
    @ApiOperation("更新教师信息")
    public void updateById(Teacher teacher) {
        teacherMapper.updateById(teacher);
    }

    /**
     * 添加教师信息
     * @param teacher 教师对象
     * @return 添加结果，成功为true，失败为false
     */
    @ApiOperation("添加教师信息")
    public void save(Teacher teacher) {
        teacherMapper.save(teacher);
    }

    /**
     * 根据教师ID删除教师信息
     * @param tid 教师ID
     * @return 删除结果，成功为true，失败为false
     */
    @ApiOperation("根据ID删除教师信息")
    public boolean deleteById(Integer tid) {
        return teacherMapper.deleteById(tid);
    }
}
