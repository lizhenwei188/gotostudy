package com.gotostudy.study.msm.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
    /**
     *  发送多媒体类型邮件
     */
    boolean sendMimeMail(String emailAddress);
}