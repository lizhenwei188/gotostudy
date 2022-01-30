package com.gotostudy.study.edu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gotostudy.study.edu.entity.SubjectEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程科目
 * 
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-17 15:49:39
 */
@Mapper
public interface SubjectDao extends BaseMapper<SubjectEntity> {
	
}
