package com.gotostudy.study.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.edu.entity.CourseEntity;
import com.gotostudy.study.edu.vo.CourseInfoVo;
import com.gotostudy.study.edu.vo.CoursePublishVo;
import com.gotostudy.study.edu.vo.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * 课程
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-18 11:01:24
 */
public interface CourseService extends IService<CourseEntity> {


    PageUtils queryPage(Map<String, Object> map, CourseQuery courseQuery);

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String courseId);

    R deleteCourseById(String courseId);

    List<CourseEntity> queryHotCourse();

    List<CourseEntity> getByTeacherId(String id);

    CourseInfoVo getAllInfoById(String id);

    CourseInfoVo getBaseCourseInfo(String courseId);
}

