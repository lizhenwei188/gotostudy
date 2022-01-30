package com.gotostudy.study.ord.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.ord.entity.OrderInfoEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-28 12:44:29
 */
public interface OrderInfoService extends IService<OrderInfoEntity> {

    String createOrder(String courseId, String memberId);
}

