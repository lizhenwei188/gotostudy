package com.gotostudy.study.sta.openfeign;

import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.sta.openfeign.impl.UsrFeignImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 53Hertz
 * @date: 2022/1/29 10:20
 * @email: lizhenwei188@foxmail.com
 * @description:
 */

@Component
@FeignClient(name = "study-usr",fallback = UsrFeignImpl.class)
public interface UsrFeign {
    @GetMapping("usr/UcenterMember/countRegister/{day}")
    R countRegister(@PathVariable("day") String day);
}
