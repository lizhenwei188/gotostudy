package com.gotostudy.study.ord.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gotostudy.study.com.utils.JwtUtils;
import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.ord.entity.OrderInfoEntity;
import com.gotostudy.study.ord.service.OrderInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 订单
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-28 12:44:29
 */
//@CrossOrigin
@RestController
@RequestMapping("ord/order")
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;

    @ApiOperation("根据课程的id生成订单,返回订单编号")
    @PostMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request) {
        String memberId = JwtUtils.getUsrIdByRequest(request);
        //创建订单，返回订单号，后面要根据订单号做支付
        String orderNo = orderInfoService.createOrder(courseId,memberId);
        return R.ok().data("orderNo", orderNo);
    }

    @ApiOperation("根据订单id查询订单详细信息，做回显数据使用")
    @PostMapping("/getOrderById/{orderId}")
    public R getOrderInfoById(@PathVariable String orderId) {
        QueryWrapper<OrderInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        OrderInfoEntity order = orderInfoService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    //根据课程id和用户id查询订单的支付状态
    @ApiOperation("根据课程id和用户id查询订单的支付状态")
    @GetMapping("/getOrderPayStatus/{courseId}/{memberId}")
    public boolean getOrderPayStatus(@PathVariable String courseId,@PathVariable String memberId) {
        QueryWrapper<OrderInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        int count = orderInfoService.count(wrapper);
        return count > 0;
    }

}
