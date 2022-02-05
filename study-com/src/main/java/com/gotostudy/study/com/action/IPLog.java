package com.gotostudy.study.com.action;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class IPLog {
    public static final String LOG_MODEL_TYPE ="actionInfo";
    /**
     * 获取客户端ip地址
     */
    public static String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("X-Real-IP");
        // 获取的也是ip
        String remoteHost = request.getRemoteHost();
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
}