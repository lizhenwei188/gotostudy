package com.gotostudy.study.sta.openfeign.impl;

import com.gotostudy.study.com.utils.resultutil.GotostudyException;
import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.sta.openfeign.UsrFeign;
import org.springframework.stereotype.Component;

/**
 * @author 53Hertz
 * @date: 2022/1/29 10:37
 * @email: lizhenwei188@foxmail.com
 * @description:
 */
@Component
public class UsrFeignImpl implements UsrFeign {

    @Override
    public R countRegister(String day) {
        throw new GotostudyException(20001,"统计模块调用用户模块错误");
    }
}
