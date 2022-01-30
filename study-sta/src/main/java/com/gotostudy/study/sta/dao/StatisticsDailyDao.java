package com.gotostudy.study.sta.dao;

import com.gotostudy.study.sta.entity.StatisticsDailyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 网站统计日数据
 * 
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-29 10:43:35
 */
@Mapper
public interface StatisticsDailyDao extends BaseMapper<StatisticsDailyEntity> {

    List<String> selectData();

    List<Integer> selectRegisterNum();

    List<Integer> selectLoginNum();

    List<Integer> selectViewNum();
}
