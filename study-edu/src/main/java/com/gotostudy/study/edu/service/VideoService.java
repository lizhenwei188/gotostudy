package com.gotostudy.study.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.edu.entity.VideoEntity;

import java.util.Map;


/**
 * 课程视频
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-18 11:01:24
 */
public interface VideoService extends IService<VideoEntity> {

    R removeVideoByCourseId(String courseId);
}

