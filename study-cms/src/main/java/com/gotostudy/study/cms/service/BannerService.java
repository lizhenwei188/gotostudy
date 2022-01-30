package com.gotostudy.study.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.cms.entity.BannerEntity;

import java.util.List;
import java.util.Map;

/**
 * 首页banner表
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-21 15:55:31
 */
public interface BannerService extends IService<BannerEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<BannerEntity> getAll();
}

