package com.gotostudy.study.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.com.utils.mybatisplus.Query;
import com.gotostudy.study.edu.dao.TeacherDao;
import com.gotostudy.study.edu.entity.TeacherEntity;
import com.gotostudy.study.edu.service.TeacherService;
import com.gotostudy.study.edu.vo.TeacherQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;



@Service("teacherService")
public class TeacherServiceImpl extends ServiceImpl<TeacherDao, TeacherEntity> implements TeacherService {

    @Override
    public PageUtils queryPage(Map<String, Object> map, TeacherQuery teacherQuery) {
        QueryWrapper<TeacherEntity> wrapper = new QueryWrapper<>();

        if (teacherQuery != null) {

            String name = teacherQuery.getName();
            Date beginTime = teacherQuery.getBeginTime();
            Date endTime = teacherQuery.getEndTime();
            Integer level = teacherQuery.getLevel();


            if (StringUtils.isNotEmpty(name)) {
                wrapper.like("name",name);
            }
            if (level != null) {
                wrapper.eq("level",level);
            }
            if (beginTime != null) {
                wrapper.ge("create_time",beginTime);
            }
            if (endTime != null) {
                wrapper.le("update_time",endTime);
            }
        }

        IPage<TeacherEntity> page = this.page(
                new Query<TeacherEntity>().getPage(map),
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