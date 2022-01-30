package com.gotostudy.study.msm.service.impl;


import com.gotostudy.study.msm.service.MailService;
import com.gotostudy.study.msm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @description: 邮件处理类
 * @author: luohanye
 **/
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    JavaMailSenderImpl mailSender;

    //springboot集成redis封装的一个对象，注入来实现验证码的时效问题
    //方便后面进行注册的时候从redis中进行取值判断验证码的正确性
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean sendMimeMail(String emailAddress) {
        String code = redisTemplate.opsForValue().get(emailAddress);
        //code为空
        if (StringUtils.isEmpty(code)) {
            code = RandomUtil.getFourBitRandom();
            //将生成的验证码存入到redis中
            redisTemplate.opsForValue().set(emailAddress, code,1, TimeUnit.MINUTES);
        }
        //创建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("注册验证码");//邮件标题
        message.setText("注册验证码是：" + code);//邮件内容
        message.setTo(emailAddress);//接收地址
        message.setFrom("lizhenwei188@foxmail.com");//发送地址
        mailSender.send(message);//发送邮件
        return true;
    }
}