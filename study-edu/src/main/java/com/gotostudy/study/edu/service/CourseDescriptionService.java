package com.gotostudy.study.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.edu.entity.CourseDescriptionEntity;

import java.util.Map;

/**
 * 课程简介
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-18 11:01:24
 */
public interface CourseDescriptionService extends IService<CourseDescriptionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

