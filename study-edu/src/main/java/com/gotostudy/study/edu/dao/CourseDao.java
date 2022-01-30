package com.gotostudy.study.edu.dao;

import com.gotostudy.study.edu.entity.CourseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gotostudy.study.edu.vo.CourseInfoVo;
import com.gotostudy.study.edu.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 课程
 * 
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-18 11:01:24
 */
@Mapper
public interface CourseDao extends BaseMapper<CourseEntity> {

    CoursePublishVo getCoursePublishInfo(@Param("courseId") String courseId);

    CourseInfoVo getAllInfoById(@Param("id") String id);
}
