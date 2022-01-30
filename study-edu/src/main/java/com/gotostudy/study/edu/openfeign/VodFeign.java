package com.gotostudy.study.edu.openfeign;

import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.edu.openfeign.impl.VodFeignImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
    相当与feign到Nacos中找service-vod这个微服务，然后根据下面的接口方法
    找到对应的实现类，然后用对象调用方法，实现相关的操作,此过程叫做服务发现
    当调用出错的时候就会调用VodClientImpl里面的实现方法
 */
@Component
@FeignClient(name = "study-vod",fallback = VodFeignImpl.class)//标注要调用的服务名称，即Nacos上注册的名称
public interface VodFeign {
    /*
        定义调用方法的路径，复制要调用的方法即可，把路径改为全路径,
        @PathVariable("videoId")这个必须要指定，且("videoId")要有videoId 这个名称随意
        根据视频的id删除视频
     */
    @DeleteMapping("/vod/video/deleteAliYunVideoById/{videoId}")
    @ApiOperation("根据视频的id删除阿里云视频")
    R deleteAliYunVideoById(@PathVariable String videoId);

    //一个课程有很多章节，一个章节有很多小节，一个小节有很多视频
    //调用此方法也可以实现循环删除多个视频，为了明显再写一个方法实现删除多个视频
    @DeleteMapping("/vod/video/deleteMultiVideo")
    @ApiOperation("删除多个视频")
    R deleteMultiVideo(@ApiParam(name = "videoList", value = "云端视频id", required = true)
                       @RequestParam("videoList") List<String> videoList);

}
