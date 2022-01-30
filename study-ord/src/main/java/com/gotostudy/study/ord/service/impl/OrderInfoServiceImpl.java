package com.gotostudy.study.ord.service.impl;

import com.gotostudy.study.com.orderVo.CourseWebVoOrder;
import com.gotostudy.study.com.orderVo.UcenterMemberOrder;
import com.gotostudy.study.ord.entity.OrderInfoEntity;
import com.gotostudy.study.ord.openfeign.EduFeign;
import com.gotostudy.study.ord.openfeign.UsrFeign;
import com.gotostudy.study.ord.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gotostudy.study.ord.dao.OrderInfoDao;
import com.gotostudy.study.ord.service.OrderInfoService;


@Service("orderInfoService")
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoDao, OrderInfoEntity> implements OrderInfoService {




    @Autowired
    private EduFeign eduFeign;

    @Autowired
    private UsrFeign usrFeign;

    //创建订单，返回订单号，后面要根据订单号做支付
    @Override
    public String createOrder(String courseId, String memberId) {
        /*
        通过远程调用根据用户id获取到用户的详细信息
         */
        UcenterMemberOrder memberInfo = usrFeign.getMemberInfoById(memberId);

        /*
        通过远程调用根据课程id获取到课程的详细信息
         */
        CourseWebVoOrder courseInfo = eduFeign.getCourseInfoOrder(courseId);

        OrderInfoEntity orderInfo = new OrderInfoEntity();
        //设置订单编号，工具类随机生成的
        orderInfo.setOrderNo(OrderNoUtil.getOrderNo());

        //设置用户相关的信息
        orderInfo.setNickname(memberInfo.getNickname());
        orderInfo.setMemberId(memberInfo.getId());
        orderInfo.setEmail(memberInfo.getEmail());
        orderInfo.setMobile(memberInfo.getMobile());

        //设置课程相关的信息
        orderInfo.setCourseId(courseInfo.getId());
        orderInfo.setCourseCover(courseInfo.getCover());
        orderInfo.setCourseTitle(courseInfo.getTitle());
        orderInfo.setTotalFee(courseInfo.getPrice());
        orderInfo.setTeacherName(courseInfo.getTeacherName());

        //设置支付类型以及支付状态
        orderInfo.setStatus(0);//0表示未支付，1表示已经支付
        orderInfo.setPayType(1);//1表示微信支付2表示支付宝支付

        baseMapper.insert(orderInfo);
        String orderNo = orderInfo.getOrderNo();

        return orderInfo.getOrderNo();
    }

}