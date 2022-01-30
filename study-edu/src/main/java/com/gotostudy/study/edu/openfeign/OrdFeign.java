package com.gotostudy.study.edu.openfeign;

import com.gotostudy.study.edu.openfeign.impl.OrdFeignImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "study-ord",fallback = OrdFeignImpl.class)
public interface OrdFeign {
    //根据课程id和用户id查询订单的支付状态
    @ApiOperation("根据课程id和用户id查询订单的支付状态")
    @GetMapping("/ord/order/getOrderPayStatus/{courseId}/{memberId}")
    boolean getOrderPayStatus(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
