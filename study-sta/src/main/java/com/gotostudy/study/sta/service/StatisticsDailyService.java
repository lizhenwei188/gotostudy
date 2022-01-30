package com.gotostudy.study.sta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gotostudy.study.sta.entity.StatisticsDailyEntity;

import java.util.List;
import java.util.Map;

/**
 * 网站统计日数据
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-29 10:43:35
 */
public interface StatisticsDailyService extends IService<StatisticsDailyEntity> {

    void registerCount(String day);

    Map<String, Object> getShowData(String type, String begin, String end);

    List<String> getData();

    List<Integer> getRegisterNum();

    List<Integer> getLoginNum();

    List<Integer> getViewNum();
}

