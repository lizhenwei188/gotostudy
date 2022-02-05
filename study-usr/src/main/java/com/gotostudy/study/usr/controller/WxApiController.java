package com.gotostudy.study.usr.controller;


import com.google.gson.Gson;
import com.gotostudy.study.com.utils.JwtUtils;
import com.gotostudy.study.com.utils.resultutil.GotostudyException;
import com.gotostudy.study.usr.entity.UcenterMemberEntity;
import com.gotostudy.study.usr.service.UcenterMemberService;
import com.gotostudy.study.usr.utils.ConstantWxKey;
import com.gotostudy.study.usr.utils.HttpClientUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @description: 微信登录接口
 * @author: 53Hertz
 **/

@Slf4j
@Controller
//@CrossOrigin//只是请求地址，而不需要返回数据，不用RestController
@RequestMapping("/api/ucenter/wx")
//@RequestMapping("/usr/wx")
@Api(description = "用户微信登录")
public class WxApiController {
    @Autowired
    private UcenterMemberService memberService;

    //生成微信登录二维码,请求一个固定的地址，不用返回R
    @GetMapping("/login")
    @ApiOperation("生成微信登录二维码")
    public String getWxCode() {


        //重定向到请求生成二维码的地址
        //固定地址，后拼接参数
//        String url = "https://open.weixin.qq.com/connect/qrconnect?appid=" +
//                ConstantWxKey.WX_OPEN_APP_ID + "&response_type=code" + "&redirect_uri=" + ConstantWxKey.WX_OPEN_REDIRECT_URL;

        // 微信开放平台授权baseUrl  %s相当于一个占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对redirect_uri进行URLEnCoder编码操作
        String redirectUrl = ConstantWxKey.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //设置%s里面的值
        String url = String.format(
                baseUrl,//参数一写固定结构，后为可变参数，相当于字符串的拼接，不容易出错
                ConstantWxKey.WX_OPEN_APP_ID,
                redirectUrl,
                "gotostudy"
        );

        return "redirect:" + url;
    }

    //测试中扫完码会到，仅在测试中，实际生产中应该到程序部署的服务器中
    //http://localhost:8150/api/ucenter/wx/callback?code=081p3JFa1NMzJA03MXFa15Np8P0p3JFd&state=atguigu
    //code唯一随机生成的值，类似与验证码，state原样传递，写的什么传过来的是什么

    //获取扫描人信息，获取数据
    @GetMapping("/callback")
//    @ResponseBody            //接收地址返回的参数
    @ApiOperation("根据扫描的微信获取微信的数据")
    public String callback(String code,String state) {//测试中必须叫callback

        try {

            //用code请求微信提供的固定地址，可以获取到两个值
            //access_token,open_id
            //access_token访问微信信息的凭证
            //open_id每个微信唯一的标识值

            //向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantWxKey.WX_OPEN_APP_ID,
                    ConstantWxKey.WX_OPEN_APP_SECRET,
                    code);
            //请求accessTokenUrl有俩个值access_token，openid
            //用httpclient请求地址，得到两个值，不用浏览器模拟出请求和响应的过程
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //accessTokenInfo为json字符串，从json中取出两个值，json数据为key:value结构
            //可以将其转换为map形式的数据，然后根据key进行取值操作
            //可以使用json的转化工具gson进行转化
            Gson gson = new Gson();

            HashMap mapAccessToken = gson.fromJson(accessTokenInfo,HashMap.class);

            String openid = (String) mapAccessToken.get("openid");
            String accessToken = (String) mapAccessToken.get("access_token");

            //将扫码人的信息加入数据库中，即为微信注册的过程
            //判断数据库中有没有这个微信的openid存中，如果有，则不进行添加操作
            UcenterMemberEntity member = memberService.getOpenIdMember(openid);

            if (StringUtils.isEmpty(member)) {//表示数据库中没有此用户，进行添加操作
                //用上述两个值，再次访问微信的另一个固定地址，可以得到用户数据
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl,
                        accessToken,
                        openid);

                //请求地址获取微信用户信息,仍为json字符串
                String userInfo = HttpClientUtils.get(userInfoUrl);

                //将其转换为map取其中的值

                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");//用户名称
                String headimgurl = (String) userInfoMap.get("headimgurl");//用户头像

                member = new UcenterMemberEntity();
                member.setAvatar(headimgurl);
                member.setNickname(nickname);
                member.setOpenid(openid);
                memberService.save(member);
            }

            //使用jwt将ucenterMember中的信息转换为token字符串，通过路径传递到前端页面进行显示，
            //解决跨域的问题，如果存到cookie中，不能进行跨域，上面那个用邮箱登录的就不可以跨域登录
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            // TODO: 2022/2/5 前端地址栏出现token值影响页面跳转
            return "redirect:http://localhost:3000?token=" + jwtToken;
        } catch (Exception e) {
            throw new GotostudyException(20001, "登录失败");
        }
    }
}
