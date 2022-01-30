package com.gotostudy.study.vod.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public interface VodService {

    //上传视频到阿里云
    String uploadAliYunVideo(MultipartFile file);

    //删除多个阿里云视频
    void removeMultiVideo(List<String> videoList);

    //删除单个阿里云视频
    void removeVideo(String videoId);
}
