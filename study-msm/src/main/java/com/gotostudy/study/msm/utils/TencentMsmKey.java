package com.gotostudy.study.msm.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description: 腾讯短信验证配置类
 * @author: 53Hertz
 **/

@Component
public class TencentMsmKey implements InitializingBean {
    @Value("${tencent.appId}")
    private String appId;

    @Value("${tencent.appKey}")
    private String appKey;

    @Value("${tencent.templateId}")
    private String templateId;

    @Value("${tencent.signName}")
    private String signName;

    @Value("${tencent.secretId}")
    private String secretId;

    @Value("${tencent.signName}")
    private String secretKey;

    public static String APP_ID;
    public static String APP_KEY;
    public static String TEMPLATE_ID;
    public static String SIGN_NAME;

    public static String SECRET_ID;
    public static String SECRET_KEY;

    @Override
    public void afterPropertiesSet() {
        APP_ID = appId;
        APP_KEY = appKey;
        TEMPLATE_ID = templateId;
        SIGN_NAME = signName;

        SECRET_ID = secretId;
        SECRET_KEY = secretKey;
    }
}
