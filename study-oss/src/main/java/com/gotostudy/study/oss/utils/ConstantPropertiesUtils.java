package com.gotostudy.study.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description: 从配置文件中获取连接OSS的信息
 * @author: 53Hertz
 **/
//@Configuration
@Component
//当项目启动时，spring中有一个接口，在加载完spring之后就会去执行这个方法
public class ConstantPropertiesUtils implements InitializingBean {
    //获取配置文件的内容
//    @Value("abc")//spring中属性的注入
//    private String abc;

    @Value("${aliyun.oss.file.endpoint}")
    private String endPoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    //定义公开常量，为了能够在外面使用
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;
    @Override//在spring加载完成之后就会执行这个方法
    public void afterPropertiesSet() throws Exception {
        END_POINT = endPoint;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
