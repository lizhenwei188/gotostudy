package com.gotostudy.study.ord.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gotostudy.study.ord.entity.PayLogEntity;

import java.util.Map;

/**
 * 支付日志表
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-27 17:47:40
 */
public interface PayLogService extends IService<PayLogEntity> {

    Map<String, Object> createNative(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}

