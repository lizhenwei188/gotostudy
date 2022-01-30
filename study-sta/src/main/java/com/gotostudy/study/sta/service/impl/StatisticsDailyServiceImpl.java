package com.gotostudy.study.sta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.sta.openfeign.UsrFeign;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gotostudy.study.sta.dao.StatisticsDailyDao;
import com.gotostudy.study.sta.entity.StatisticsDailyEntity;
import com.gotostudy.study.sta.service.StatisticsDailyService;


@Service("statisticsDailyService")
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyDao, StatisticsDailyEntity> implements StatisticsDailyService {

    @Autowired
    private UsrFeign usrFeign;

    @Override
    public void registerCount(String day) {
        /*
        添加数据之前先删除相同日期的数据，以保证数据的最新
        也可以修改之前的数据，达到相同的目的
         */
        QueryWrapper<StatisticsDailyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        //远程调用得到某一天的人数
        R countRegister = usrFeign.countRegister(day);
        Map<String, Object> registerMap = countRegister.getData();
        Integer count = (Integer) registerMap.get("count");

        //把数据添加到数据库中
        StatisticsDailyEntity statisticsDaily = new StatisticsDailyEntity();
        statisticsDaily.setRegisterNum(count);//设置注册的人数
        statisticsDaily.setDateCalculated(day);//统计的是那一天的注册人数

        //模拟数据
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100,200));

        //将数据插入到数据库中
        baseMapper.insert(statisticsDaily);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDailyEntity> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated",type);
        List<StatisticsDailyEntity> dailyList = baseMapper.selectList(wrapper);

        /*
        前端需要日期以及日期对应的数量，需要数组类型的json
        对应后端代码的list集合
        前端的json对象对应后端代码的对象或者map
         */

        //存放日期
        List<String> dateCalculatedList = new ArrayList<>();
        //存放数量
        List<Integer> numDataList = new ArrayList<>();

        //遍历查询出来的两部分数据，放入到以上的两个数组中
        for (StatisticsDailyEntity statisticsDaily : dailyList) {
            dateCalculatedList.add(statisticsDaily.getDateCalculated());
            switch (type) {
                case "login_num" :
                    numDataList.add(statisticsDaily.getLoginNum());
                    break;
                case "register_num" :
                    numDataList.add(statisticsDaily.getRegisterNum());
                    break;
                case "video_view_num" :
                    numDataList.add(statisticsDaily.getVideoViewNum());
                    break;
                default:
                    numDataList.add(statisticsDaily.getCourseNum());
                    break;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("dateCalculatedList",dateCalculatedList);
        map.put("numDataList",numDataList);

        return map;
    }

    @Override
    public List<String> getData() {
        return baseMapper.selectData();
    }

    @Override
    public List<Integer> getRegisterNum() {
        return baseMapper.selectRegisterNum();
    }

    @Override
    public List<Integer> getLoginNum() {
        return baseMapper.selectLoginNum();
    }

    @Override
    public List<Integer> getViewNum() {
        return baseMapper.selectViewNum();
    }
}