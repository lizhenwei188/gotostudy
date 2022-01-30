package com.gotostudy.study.edu.service.impl;

import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.com.utils.mybatisplus.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gotostudy.study.edu.dao.CourseDescriptionDao;
import com.gotostudy.study.edu.entity.CourseDescriptionEntity;
import com.gotostudy.study.edu.service.CourseDescriptionService;


@Service("courseDescriptionService")
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionDao, CourseDescriptionEntity> implements CourseDescriptionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CourseDescriptionEntity> page = this.page(
                new Query<CourseDescriptionEntity>().getPage(params),
                new QueryWrapper<CourseDescriptionEntity>()
        );

        return new PageUtils(page);
    }

}