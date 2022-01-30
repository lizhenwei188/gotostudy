package com.gotostudy.study.msm.controller;

import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.msm.service.MailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 邮箱验证登录
 * @author: 53Hertz
 **/

//@CrossOrigin
@RestController
@RequestMapping("/msm/email")
@Api(description = "邮箱验证服务接口")
public class EmailMsmController {
    @Autowired
    private MailService mailService;

    @PostMapping("/send/{emailAddress}")
    public R sendMailCode(@PathVariable String emailAddress) {
        try {
            mailService.sendMimeMail(emailAddress);
            return R.ok().message("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error().message("发送失败");
        }
    }
}
