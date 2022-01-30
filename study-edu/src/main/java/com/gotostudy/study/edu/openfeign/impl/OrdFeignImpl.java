package com.gotostudy.study.edu.openfeign.impl;

import com.gotostudy.study.edu.openfeign.OrdFeign;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: 53Hertz
 **/

@Component
public class OrdFeignImpl implements OrdFeign {
    @Override
    public boolean getOrderPayStatus(String courseId, String memberId) {
        return false;
    }
}
