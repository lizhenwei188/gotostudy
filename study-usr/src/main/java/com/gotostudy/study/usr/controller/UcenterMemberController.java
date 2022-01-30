package com.gotostudy.study.usr.controller;

import com.gotostudy.study.com.orderVo.UcenterMemberOrder;
import com.gotostudy.study.com.utils.JwtUtils;
import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.usr.entity.UcenterMemberEntity;
import com.gotostudy.study.usr.service.UcenterMemberService;
import com.gotostudy.study.usr.vo.RegisterVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 53Hertz
 * @date: 2022/1/24 15:44
 * @email: lizhenwei188@foxmail.com
 * @description:
 */
//@CrossOrigin
@RestController
@RequestMapping("usr/UcenterMember")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    @PostMapping("/userLogin")//一定要记住@RequestBody是postMapping请求
    @ApiOperation("用户登录方法")
    public R userLogin(@RequestBody UcenterMemberEntity member) {
        //返回token值，使用jwt生成
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    @PostMapping("/userRegister")
    @ApiOperation("用户注册方法")
    public R userRegister(@RequestBody RegisterVo registerVo) {
        boolean result = memberService.register(registerVo);
        if (result) {
            return R.ok().message("注册成功");
        } else {
            return R.error().message("注册失败");
        }
    }

    //根据token值取出里面的用户信息
    @ApiOperation("根据token值获取出里面的用户id")
    @GetMapping("/getMemberInfoByToken")
    public R getMemberInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getUsrIdByRequest(request);
        UcenterMemberEntity member = memberService.getById(memberId);
        return R.ok().data("memberInfo",member);
    }

    /*
    根据用户的id获取用户的详细信息，用于后面的评论以及支付远程调用
     */
    @ApiOperation("根据用户id获取用户的详细信息")
    @PostMapping("/getMemberInfoById/{memberId}")
    public UcenterMemberOrder getMemberInfoById(@PathVariable String memberId) {
        UcenterMemberEntity member = memberService.getById(memberId);
        UcenterMemberOrder ucenterMemberEntityOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member, ucenterMemberEntityOrder);
        return ucenterMemberEntityOrder;
    }

    /*
    查询某一天的注册人数
     */
    @ApiOperation("查询某一天的注册人数")
    @GetMapping("/countRegister/{day}")
    public R countRegister(@PathVariable String day) {
        Integer count = memberService.countRegister(day);
        System.out.println(count);
        return R.ok().data("count", count);
    }
}
