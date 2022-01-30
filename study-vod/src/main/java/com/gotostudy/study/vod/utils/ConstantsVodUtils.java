package com.gotostudy.study.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description: 工具类
 * @author: 53Hertz
 **/

@Component
public class ConstantsVodUtils implements InitializingBean {
    @Value("${aliyun.vod.file.keyid}")
    private String accessKeyId;

    @Value("${aliyun.vod.file.keysecret}")
    private String accessKeySecret;

    //定义公开常量
    public static String KEY_ID;
    public static String KEY_SECRET;

    @Override
    public void afterPropertiesSet() {
        KEY_ID = accessKeyId;
        KEY_SECRET = accessKeySecret;
    }
}
