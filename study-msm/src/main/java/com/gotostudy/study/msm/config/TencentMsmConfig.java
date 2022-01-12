package com.gotostudy.study.msm.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TencentMsmConfig implements InitializingBean {
    @Value("${tencent.appid}")
    private Integer appid;

    @Value("${tencent.appkey}")
    private String appkey;

    @Value("${tencent.templateId}")
    private Integer templateId;

    @Value("${tencent.signname}")
    private String signname;

    public static Integer APP_ID;
    public static String APPK_KEY;
    public static Integer TEMPLATE_ID;
    public static String SIGN_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = appid;
        APPK_KEY = appkey;
        TEMPLATE_ID = templateId;
        SIGN_NAME = signname;
    }
}
