package com.gotostudy.study.edu.openfeign;

import com.gotostudy.study.com.orderVo.UcenterMemberOrder;
import com.gotostudy.study.edu.openfeign.impl.UsrFeignImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "study-usr",fallback = UsrFeignImpl.class)
public interface UsrFeign {

    @ApiOperation("根据用户id获取用户的详细信息")
    @PostMapping("/usr/member/getMemberInfoById/{memberId}")
    UcenterMemberOrder getMemberInfoById(@PathVariable("memberId") String memberId);
}
