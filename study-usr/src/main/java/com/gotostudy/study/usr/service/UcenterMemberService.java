package com.gotostudy.study.usr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.usr.entity.UcenterMemberEntity;
import com.gotostudy.study.usr.vo.RegisterVo;

import java.util.Map;

/**
 * 会员表
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-24 15:15:17
 */
public interface UcenterMemberService extends IService<UcenterMemberEntity> {

    String login(UcenterMemberEntity member);

    boolean register(RegisterVo registerVo);

    Integer countRegister(String day);

    UcenterMemberEntity getOpenIdMember(String openid);
}

