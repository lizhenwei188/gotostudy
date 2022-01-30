package com.gotostudy.study.sta.controller;

import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.sta.service.StatisticsDailyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 网站统计日数据
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-29 10:43:35
 */
//@CrossOrigin
@RestController
@RequestMapping("sta/statistic")
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService dailyService;

    @GetMapping("/registerCount/{day}")
    @ApiOperation("统计某一天的注册人数")
    public R registerCount(@PathVariable String day) {
        dailyService.registerCount(day);
        return R.ok();
    }

    /*
    图表数据，显示两部分数据，x轴json日期数组，y轴json数量数组
     */

//    @GetMapping("/showData/{type}/{begin}/{end}")
    @GetMapping("/showData")
    @ApiOperation("显示两部分数据，x轴json日期数组，y轴json数量数组")
//    public R showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end) {
    public R showData() {
//        Map<String,Object> map = dailyService.getShowData(type,begin,end);

        HashMap<String, Object> map = new HashMap<>();

        List<String> dateList = dailyService.getData();
        List<Integer> registerNumList = dailyService.getRegisterNum();
        List<Integer> loginNumList = dailyService.getLoginNum();
        List<Integer> viewList = dailyService.getViewNum();

        map.put("dateList", dateList);
        map.put("registerNumList", registerNumList);
        map.put("loginNumList", loginNumList);
        map.put("viewList", viewList);

        return R.ok().data(map);
    }




}
