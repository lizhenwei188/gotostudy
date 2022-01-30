package com.gotostudy.study.ord.openfeign;


import com.gotostudy.study.com.orderVo.CourseWebVoOrder;
import com.gotostudy.study.ord.openfeign.impl.EduFeignImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "study-edu",fallback = EduFeignImpl.class)
public interface EduFeign {
    @PostMapping("/edu/course/getCourseInfoOrder/{courseId}")
    @ApiOperation("根据课程的id查询课程的详细信息，用于后面的支付微服务")
    CourseWebVoOrder getCourseInfoOrder(@PathVariable("courseId") String courseId);
}
