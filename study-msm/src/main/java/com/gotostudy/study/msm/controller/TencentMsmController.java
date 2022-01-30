package com.gotostudy.study.msm.controller;


import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.msm.service.TencentMsmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: 53Hertz
 **/

@Slf4j
//@CrossOrigin
@RestController
@RequestMapping("/msm/message")
@Api(description = "腾讯手机短信验证码的发送")
public class TencentMsmController {
    @Autowired
    private TencentMsmService tencentMsmService;

    @PostMapping("/sendMsg/{phoneNum}")
    @ApiOperation("腾讯发送短信验证码的接口")
    public R sendMsmTencent(@PathVariable String phoneNum) {
        return tencentMsmService.sendMsm(phoneNum);
    }
}
