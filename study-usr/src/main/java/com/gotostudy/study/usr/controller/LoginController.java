package com.gotostudy.study.usr.controller;


import com.gotostudy.study.com.utils.resultutil.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 模拟登录
 * @author: 53Hertz
 **/

//@CrossOrigin
@RestController
@RequestMapping("/usr/back")
@Api(description = "讲师的逻辑删除")
public class LoginController {
    //login的方法
    @PostMapping("/login")
    public R login() {

        return R.ok().data("token", "admin");
    }

    //info的方法
    @GetMapping("/info")
    public R info() {
        String[] strings = new String[1];
        strings[0] = "admin";
        return R.ok().data("roles", strings).data("name", "刘德华").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
