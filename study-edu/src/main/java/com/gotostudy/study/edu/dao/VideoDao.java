package com.gotostudy.study.edu.dao;

import com.gotostudy.study.edu.entity.VideoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程视频
 * 
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-18 11:01:24
 */
@Mapper
public interface VideoDao extends BaseMapper<VideoEntity> {
	
}
