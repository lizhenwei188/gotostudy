package com.gotostudy.study.edu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.edu.entity.TeacherEntity;

import java.util.Map;

/**
 * 讲师
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-11 15:50:21
 */
public interface TeacherService extends IService<TeacherEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

