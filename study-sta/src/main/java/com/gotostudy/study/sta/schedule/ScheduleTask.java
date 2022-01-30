package com.gotostudy.study.sta.schedule;


import com.gotostudy.study.sta.service.StatisticsDailyService;
import com.gotostudy.study.sta.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description: 定时任务类
 * @author: 53Hertz
 **/

@Component
@Slf4j
public class ScheduleTask {
    @Autowired
    private StatisticsDailyService dailyService;

    //每隔5秒执行一次这个方法
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void test1() {
//        log.warn("*********************");
//    }

    //每天凌晨1点执行定时任务,把前一天统计数据统计到表中
    @Scheduled(cron = "0 0 1 * * ? ")
    public void test2() {
        dailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
