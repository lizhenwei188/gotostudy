package com.gotostudy.study.ord.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.gotostudy.study.com.utils.resultutil.GotostudyException;;
import com.gotostudy.study.ord.entity.OrderInfoEntity;
import com.gotostudy.study.ord.service.OrderInfoService;
import com.gotostudy.study.ord.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gotostudy.study.ord.dao.PayLogDao;
import com.gotostudy.study.ord.entity.PayLogEntity;
import com.gotostudy.study.ord.service.PayLogService;


@Service("payLogService")
public class PayLogServiceImpl extends ServiceImpl<PayLogDao, PayLogEntity> implements PayLogService {

    @Autowired
    private OrderInfoService orderService;

    //生成微信支付二维码
    @Override
    public Map<String, Object> createNative(String orderNo) {

        QueryWrapper<OrderInfoEntity> wrapper = null;
        try {
            //根据订单编号，查询出订单的详细信息
            wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            OrderInfoEntity order = orderService.getOne(wrapper);

            //使用map设置生成二维码需要的参数
            Map<String, String> m = new HashMap<>();
            //1、设置支付参数
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());//引入jar包带的工具类，使每个二维码都不一样
            m.put("body", order.getCourseTitle());//二维码要显示的名称
            m.put("out_trade_no", orderNo);//二维码的编号
            //价格，使价格变为字符串
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            //域名
            m.put("spbill_create_ip", "127.0.0.1");
            //支付以后的回调地址
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            //支付的类型，本项目采用根据价格生成二维码的方式
            m.put("trade_type", "NATIVE");

            //2、HTTPClient来根据URL访问第三方接口并且传递参数，将上述参数变为xml形式进行传送
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");

            //设置xml格式的参数   把map集合的数据根据key做个加密，并转为xml格式的数据
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            //默认不支持https访问
            client.setHttps(true);
            //执行发送请求
            client.post();
            //得到结果,content为xml格式
            String content = client.getContent();
            //把xml格式的数据转换为map类型的数据，进行最后的返回
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);

            /*
            resultMap里面包含有二维码的地址，但通常还需要一些其他的课程信息
            重新做一次封装，带有课程的信息
             */

            //4、封装返回结果集 key的名称自己写，value的名称固定

            Map<String, Object> map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            //返回二维码操作状态码
            map.put("result_code", resultMap.get("result_code"));
            //二维码地址
            map.put("code_url", resultMap.get("code_url"));

            return map;
        } catch (Exception e) {
            throw new GotostudyException(20001,"生成支付二维码出现错误");
        }
    }

    //根据订单号查询订单的支付状态
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {

        try {
            //1、封装参数
            Map<String, String> m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据
            String xml = client.getContent();
            //6、转成Map
            //7、返回
            return WXPayUtil.xmlToMap(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //向数据库中支付表中添加支付记录，并修改订单表中的支付状态为1
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //从map中获取订单编号  "out_trade_no" 为固定值
        String tradeNo = map.get("out_trade_no");

        //根据订单号查询订单信息
        QueryWrapper<OrderInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", tradeNo);
        OrderInfoEntity order = orderService.getOne(wrapper);

        //跟新订单表中的状态,如果状态为1则表示订单已经修改，不需要进行操作
        if (order.getStatus() == 1) {
            return;
        }

        //如果状态不为1，则需要进行修改操作
        order.setStatus(1);
        orderService.updateById(order);

        //记录支付日志
        PayLogEntity payLog=new PayLogEntity();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));//支付流水
        payLog.setAttr(JSONObject.toJSONString(map));//map中的其他值转为json字符串存到数据库中
        baseMapper.insert(payLog);//插入到支付日志表

    }

}