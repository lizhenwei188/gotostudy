package com.gotostudy.study.vod.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.gotostudy.study.com.utils.resultutil.GotostudyException;
import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.vod.service.VodService;
import com.gotostudy.study.vod.utils.ConstantsVodUtils;
import com.gotostudy.study.vod.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @description:
 * @author: 53Hertz
 **/

//@CrossOrigin
@RequestMapping("/vod/video")
@RestController
@Api(description="阿里云视频点播服务")
public class VodController {
    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("/uploadAliYunVideo")
    @ApiOperation(value = "文件上传")
    public R uploadAliYunVideo(MultipartFile file) {
        String videoId = vodService.uploadAliYunVideo(file);
        return R.ok().data("videoSourceId", videoId);
    }

    //根据视频的id删除视频
    //一个课程有很多章节，一个章节有很多小节，一个小节有很多视频
    //调用此方法也可以实现循环删除多个视频，为了明显再写一个方法实现删除多个视频
    @DeleteMapping("/deleteAliYunVideoById/{videoId}")
    @ApiOperation("根据视频的id删除阿里云视频")
    public R deleteAliYunVideoById(@PathVariable String videoId) {
        //删除单个的阿里云视频
        vodService.removeVideo(videoId);
        return R.ok();
    }

    //一个课程有很多章节，一个章节有很多小节，一个小节有很多视频
    //调用此方法也可以实现循环删除多个视频，为了明显再写一个方法实现删除多个视频
    @DeleteMapping("/deleteMultiVideo")
    @ApiOperation("删除多个视频")
    public R deleteMultiVideo(@ApiParam(name = "videoList",value = "云端视频id",required = true)
                              @RequestParam("videoList") List<String> videoList) {
        vodService.removeMultiVideo(videoList);
        return R.ok();
    }

    /*
    根据视频的id获取视频的凭证
     */
    @GetMapping("/getPlayAuth/{id}")
    @ApiOperation("根据视频的id获取视频的凭证")
    public R getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    InitVodClient.initVodClient(ConstantsVodUtils.KEY_ID, ConstantsVodUtils.KEY_SECRET);
            //创建获取凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            //向request里面设置视频的id
            request.setVideoId(id);

            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        } catch (Exception e) {
            throw new GotostudyException(20001, "获取视频播放凭证失败啦");
        }
    }
}
