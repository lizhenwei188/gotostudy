package com.gotostudy.study.ord.openfeign.impl;


import com.gotostudy.study.com.orderVo.UcenterMemberOrder;
import com.gotostudy.study.ord.openfeign.UsrFeign;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: 53Hertz
 **/

@Component
public class UsrFeignImpl implements UsrFeign {
    @Override
    public UcenterMemberOrder getMemberInfoById(String memberId) {

        return null;
    }
}
