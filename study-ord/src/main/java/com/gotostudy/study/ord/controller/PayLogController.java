package com.gotostudy.study.ord.controller;

import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gotostudy.study.ord.entity.PayLogEntity;
import com.gotostudy.study.ord.service.PayLogService;
import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.com.utils.resultutil.R;



/**
 * 支付日志表
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-27 17:47:40
 */
//@CrossOrigin
@RestController
@RequestMapping("ord/paylog")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    @ApiOperation("生成微信支付二维码")
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        /*
        返回值包括二维码的地址，以及课程的其他相关信息
         */
        Map<String, Object> map = payLogService.createNative(orderNo);

        return R.ok().data(map);
    }

    //查询订单支付的状态
    @GetMapping("/queryPayStatus/{orderNo}")
    @ApiOperation("查询订单支付的状态")
    public R queryPayStatus(@PathVariable String orderNo) {
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return R.error().message("订单支付出错啦");
        }
        //如果返回值不为空，里面有订单支付的状态,当支付成功以后，
        // map.get("trade_state"))就会为SUCCESS
        if ("SUCCESS".equals(map.get("trade_state"))) {
            //添加支付记录到数据库中，并更改订单表中的支付状态
            payLogService.updateOrderStatus(map);

            return R.ok().message("支付成功！");
        }
        return R.ok().code(25000).message("正在支付中......");
    }

}
