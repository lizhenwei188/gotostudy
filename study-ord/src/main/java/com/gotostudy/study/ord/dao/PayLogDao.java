package com.gotostudy.study.ord.dao;

import com.gotostudy.study.ord.entity.PayLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付日志表
 * 
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-27 17:47:40
 */
@Mapper
public interface PayLogDao extends BaseMapper<PayLogEntity> {
	
}
