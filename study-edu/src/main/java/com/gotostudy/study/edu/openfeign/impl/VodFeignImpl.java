package com.gotostudy.study.edu.openfeign.impl;


import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.edu.openfeign.VodFeign;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: 53Hertz
 **/

@Component
public class VodFeignImpl implements VodFeign {
    @Override
    public R deleteAliYunVideoById(String videoId) {
        //如果删除单个视频出错的话这个方法将会执行
        return R.error().message("删除视频的服务出错啦！！！");
    }

    @Override
    public R deleteMultiVideo(List<String> videoList) {
        //如果删除课程同时删除里面的多个视频出错的话这个方法将会执行
        return R.error().message("删除视频的服务出错啦！！！");
    }
}
