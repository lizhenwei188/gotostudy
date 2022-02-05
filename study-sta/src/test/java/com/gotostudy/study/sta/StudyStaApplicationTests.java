package com.gotostudy.study.sta;

import com.gotostudy.study.sta.service.StatisticsDailyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class StudyStaApplicationTests {

    @Autowired
    private StatisticsDailyService dailyService;

    @Test
    void contextLoads() {

        List<String> dateList = dailyService.getData();
        List<Integer> registerNumList = dailyService.getRegisterNum();
        List<Integer> loginNumList = dailyService.getLoginNum();
        List<Integer> viewList = dailyService.getViewNum();

        System.out.println("_______________");





    }

}
