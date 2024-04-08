package com.zhiend.student_server.service;

import com.zhiend.student_server.entity.Course;
import com.zhiend.student_server.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhiend
 * @Date: 2024/04/08
 * @Description: 课程服务类
 * @Version: 1.0.0
 */
@Service
public class CourseService {
    @Autowired
    private CourseMapper courseMapper;

    /**
     * 根据搜索条件查询课程
     *
     * @param map 搜索条件的Map，包括课程ID(cid)、课程名称(cname)、模糊搜索标志(fuzzy)、分数下限(lowBound)、分数上限(highBound)
     * @return 符合条件的课程列表
     */
    public List<Course> findBySearch(Map<String, String> map) {
        Integer cid = null;
        Integer lowBound = null;
        Integer highBound = null;
        Integer fuzzy = 0;
        String cname = null;

        if (map.containsKey("cid")) {
            try {
                cid = Integer.parseInt(map.get("cid"));
            } catch (Exception e) {
            }
        }
        if (map.containsKey("lowBound")) {
            try {
                lowBound = Integer.parseInt(map.get("lowBound"));
            } catch (Exception e) {
            }
        }
        if (map.containsKey("highBound")) {
            try {
                highBound = Integer.valueOf(map.get("highBound"));
            } catch (Exception e) {
            }
        }
        if (map.containsKey("cname")) {
            cname = map.get("cname");
        }
        if (map.containsKey("fuzzy")) {
            fuzzy = (map.get("fuzzy").equals("true")) ? 1 : 0;
        }
        System.out.println("查询课程 map：" + map);
        System.out.println(cid + " " + cname + " " + fuzzy + " " + lowBound + " " + highBound);
        return courseMapper.findBySearch(cid, cname, fuzzy, lowBound, highBound);
    }

    /**
     * 根据课程ID查询课程
     *
     * @param cid 课程ID
     * @return 符合条件的课程列表
     */
    public List<Course> findBySearch(Integer cid) {
        return courseMapper.findBySearch(cid, null, 0, null, null);
    }

    /**
     * 根据课程ID查询课程
     *
     * @param cid 课程ID
     * @return 符合条件的课程列表
     */
    public List<Course> findById(Integer cid) {
        HashMap<String, String> map = new HashMap<>();
        if (cid != null)
            map.put("cid", String.valueOf(cid));
        return findBySearch(map);
    }

    /**
     * 更新课程信息
     *
     * @param course 待更新的课程对象
     * @return 更新是否成功
     */
    public boolean updateById(Course course) {
        return courseMapper.updateById(course);
    }

    /**
     * 插入新课程
     *
     * @param course 待插入的课程对象
     * @return 插入是否成功
     */
    public boolean insertCourse(Course course) {
        return courseMapper.insertCourse(course);
    }

    /**
     * 根据课程ID删除课程
     *
     * @param cid 待删除课程的ID
     * @return 删除是否成功
     */
    public boolean deleteById(Integer cid) {
        return courseMapper.deleteById(cid);
    }
}
