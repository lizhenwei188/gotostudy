package com.gotostudy.study.com.constant;

import java.util.HashMap;

/**
 * @title: ActionDetail
 * @Author 53Hertz
 * @Date: 2021/12/9 13:38
 * @Version 1.0
 */

public class ActionDetail {

    // 自定义操作详情
    public static HashMap<String,String> getActionDetails() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("login", "登录。");
        hashMap.put("logout", "退出登录。");

        hashMap.put("realTimePng", "下载实时监测数据图片。");
        hashMap.put("realTimeExcel", "下载实时监测数据表格。");

        hashMap.put("exceedTimePng", "下载各因子超标天（次）数图片。");
        hashMap.put("exceedTimeExcel", "下载各因子超标天（次）数表格。");

        hashMap.put("exceedRatePng", "下载各因子超标率表格。");
        hashMap.put("exceedRateExcel", "下载各因子超标率表格。");

        hashMap.put("factorDataPng", "下载" + "/" + "变化趋势图图片。");
        hashMap.put("factorDataExcel", "下载" + "/" + "变化趋势图表格。");

        hashMap.put("start", "启动设备。");
        hashMap.put("stop", "关闭设备。");
        hashMap.put("startFix", "执行远程调试操作" + "/" + "。");
        hashMap.put("stopFix", "取消执行远程调试操作" + "/" + "。");
        hashMap.put("modelFix", "修改运行模式为" + "/" + "。");

        hashMap.put("editFactor", "编辑" + "/" + "上限值为" + "/" + "，下限值为" + "/" + "。");
        hashMap.put("editWater", "编辑" + "/" + "一类水为" + "/" + "，二类水为" + "/" + "，三类水为" + "/" + "，四类水为" + "/" + "。");

        hashMap.put("editName", "修改姓名为" + "/" + "。");
        hashMap.put("editPhone", "修改手机号码为" + "/" + "。");
        hashMap.put("editDesc", "修改负责内容描述为" + "/" + "。");
        return hashMap;
    }
}
