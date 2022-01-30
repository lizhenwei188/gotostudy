package com.gotostudy.study.ord.dao;

import com.gotostudy.study.ord.entity.OrderInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-28 12:44:29
 */
@Mapper
public interface OrderInfoDao extends BaseMapper<OrderInfoEntity> {
	
}
