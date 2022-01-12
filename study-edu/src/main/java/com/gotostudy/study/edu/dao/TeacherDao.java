package com.gotostudy.study.edu.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gotostudy.study.edu.entity.TeacherEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 讲师
 * 
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-11 15:50:21
 */
@Mapper
public interface TeacherDao extends BaseMapper<TeacherEntity> {
	
}
