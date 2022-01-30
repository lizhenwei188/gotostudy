package com.gotostudy.study.ord.openfeign.impl;


import com.gotostudy.study.com.orderVo.CourseWebVoOrder;
import com.gotostudy.study.ord.openfeign.EduFeign;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: 53Hertz
 **/

@Component
public class EduFeignImpl implements EduFeign {
    @Override
    public CourseWebVoOrder getCourseInfoOrder(String courseId) {
        return null;
    }
}
