package com.gotostudy.study.usr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gotostudy.study.com.utils.JwtUtils;
import com.gotostudy.study.com.utils.MD5;
import com.gotostudy.study.com.utils.resultutil.GotostudyException;
import com.gotostudy.study.usr.dao.UcenterMemberDao;
import com.gotostudy.study.usr.entity.UcenterMemberEntity;
import com.gotostudy.study.usr.service.UcenterMemberService;
import com.gotostudy.study.usr.vo.RegisterVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service("ucenterMemberService")
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberDao, UcenterMemberEntity> implements UcenterMemberService {

    //注入redis对象，注册的时候可以判断验证码的正确性
    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override//用户登录的方法
    public String login(UcenterMemberEntity member) {
        String email = member.getEmail();
        String mobile = member.getMobile();
        String password = member.getPassword();

        //邮箱或密码的非空判断
        if (StringUtils.isEmpty(password)) {
            throw new GotostudyException(20001, "密码不能为空");
        }if (StringUtils.isEmpty(email) && StringUtils.isEmpty(mobile)) {
            throw new GotostudyException(20001, "邮箱或手机号不能为空");
        }

        UcenterMemberEntity ucenterMember = null;
        if (StringUtils.isEmpty(email)) {
            QueryWrapper<UcenterMemberEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("mobile",mobile);
            ucenterMember = baseMapper.selectOne(wrapper);
        }if (StringUtils.isEmpty(mobile)) {
            QueryWrapper<UcenterMemberEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("email",email);
            ucenterMember = baseMapper.selectOne(wrapper);
        }

        //判断用户是否存在
        if (StringUtils.isEmpty(ucenterMember)) {//
            throw new GotostudyException(20001, "该用户不存在");
        }
        String memberPassword = ucenterMember.getPassword();
        /*
        判断密码是否正确
        数据库中的密码是经过加密处理以后的，不能直接进行比较
        把输入的密码进行加密，然后在和数据库中的数据进行比较
        通常采用md5进行加密，md5只能进行加密，不能解密
        */

        if (!MD5.encrypt(password).equals(memberPassword)) {
            throw new GotostudyException(20001, "密码不正确，请重新输入");
        }

        //判断用户是否禁用
        if ("1".equals(ucenterMember.getIsDisabled().toString())) {//为true表示用户处于禁用状态
            throw new GotostudyException(20001, "该用户已经禁用");
        }

        //走到这里表示用户已经登录成功
        //生成jwt字符串token，使用common里定义的jwt工具类
        return JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

    }

    @Override//用户注册的方法
    public boolean register(RegisterVo registerVo) {

        String code = registerVo.getCode();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        String email = registerVo.getEmail();
        String mobile = registerVo.getMobile();

        //判断值是否为空
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            throw new GotostudyException(20001,"信息不能为空哦!");
        }if (StringUtils.isEmpty(email) && StringUtils.isEmpty(mobile)) {
            throw new GotostudyException(20001,"请输入邮箱或手机号码!");
        }
        //判断验证码是否正确，输入的验证码会被存到redis中，可以从redis中取值进行判断
        String redisEmailCode = redisTemplate.opsForValue().get(email);
        String redisMobileCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisEmailCode) && !code.equals(redisMobileCode)) {
            throw new GotostudyException(20001, "验证码不正确，请重新输入");
        }

        if (StringUtils.isEmpty(mobile)) {
            //判断邮箱号是否重复，不能进行重复添加
            QueryWrapper<UcenterMemberEntity> emailWrapper = new QueryWrapper<>();
            emailWrapper.eq("email", email);
            Integer emailCount = baseMapper.selectCount(emailWrapper);//查询有没有，并不需要将数据查询出来
            if (emailCount > 0) {
                throw new GotostudyException(20001, "该邮箱已经注册过了,请重新输入");
            }
        }

        if (StringUtils.isEmpty(email)) {
            //判断手机号是否重复，不能进行重复添加
            QueryWrapper<UcenterMemberEntity> mobileWrapper = new QueryWrapper<>();
            mobileWrapper.eq("mobile", mobile);
            Integer mobileCount = baseMapper.selectCount(mobileWrapper);//查询有没有，并不需要将数据查询出来
            if (mobileCount > 0) {
                throw new GotostudyException(20001, "该手机号已经注册过了,请重新输入");
            }
        }

        //将用户数据加入到数据库中,但密码要用md5进行加密以后才能存到数据库中
        UcenterMemberEntity ucenterMember = new UcenterMemberEntity();
        BeanUtils.copyProperties(registerVo,ucenterMember);
        //将密码进行md5加密操作
        String md5password = MD5.encrypt(ucenterMember.getPassword());
        ucenterMember.setPassword(md5password);
        ucenterMember.setIsDisabled(0);//用户不禁用
        //暂时先设置用户的默认头像
        ucenterMember.setAvatar("https://gostudyedu.oss-cn-shanghai.aliyuncs.com/21/03/14/c0b3c52d379048ad812c7b388a321d32Picture005.jpg");
        int result = baseMapper.insert(ucenterMember);

        return result == 1;

    }


    //根据openId到数据库中查看是否有此用户
    @Override
    public UcenterMemberEntity getOpenIdMember(String openidUser) {
        QueryWrapper<UcenterMemberEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openidUser);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Integer countRegister(String day) {

        return baseMapper.countRegisterDay(day);
    }

}