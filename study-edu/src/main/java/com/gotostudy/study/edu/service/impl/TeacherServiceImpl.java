package com.gotostudy.study.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.com.utils.mybatisplus.Query;
import com.gotostudy.study.edu.dao.TeacherDao;
import com.gotostudy.study.edu.entity.TeacherEntity;
import com.gotostudy.study.edu.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service("teacherService")
public class TeacherServiceImpl extends ServiceImpl<TeacherDao, TeacherEntity> implements TeacherService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<TeacherEntity> wrapper = new QueryWrapper<>();

        String name = (String) params.get("name");
        String level = (String) params.get("level");
        String begin = (String) params.get("begin");
        String end = (String) params.get("end");

        if (StringUtils.isNotEmpty(name)) {
            wrapper.like("name",name);
        }
        if (StringUtils.isNotEmpty(level)) {
            wrapper.eq("level",level);
        }
        if (StringUtils.isNotEmpty(begin)) {
            wrapper.ge("create_time",begin);
        }
        if (StringUtils.isNotEmpty(end)) {
            wrapper.le("update_time",begin);
        }

        IPage<TeacherEntity> page = this.page(
                new Query<TeacherEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    @Cacheable(value = "teacher", key = "'selectHotTeacher'")
    public List<TeacherEntity> queryHotTeacher() {
        //查询前4条热门名师
        QueryWrapper<TeacherEntity> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        return baseMapper.selectList(wrapper);
    }

}