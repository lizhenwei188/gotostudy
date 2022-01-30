package com.gotostudy.study.oss.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface OssService {

    String uploadFileAvatar(MultipartFile file);
}
