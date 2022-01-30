package com.gotostudy.study.edu.service.impl;

import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.com.utils.mybatisplus.Query;
import com.gotostudy.study.com.utils.resultutil.GotostudyException;
import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.edu.entity.CourseDescriptionEntity;
import com.gotostudy.study.edu.entity.SubjectEntity;
import com.gotostudy.study.edu.entity.TeacherEntity;
import com.gotostudy.study.edu.service.*;
import com.gotostudy.study.edu.vo.CourseInfoVo;
import com.gotostudy.study.edu.vo.CoursePublishVo;
import com.gotostudy.study.edu.vo.CourseQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gotostudy.study.edu.dao.CourseDao;
import com.gotostudy.study.edu.entity.CourseEntity;
import org.springframework.util.StringUtils;


@Service("courseService")
public class CourseServiceImpl extends ServiceImpl<CourseDao, CourseEntity> implements CourseService {

    @Autowired
    private CourseInfoVo courseInfoVo;

    @Autowired
    private CourseEntity courseEntity;

    @Autowired
    private CourseDescriptionEntity courseDescriptionEntity;


    // ------------------------------------------------


    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private SubjectService  subjectService;

    @Autowired
    private TeacherService  teacherService;

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Override
    public PageUtils queryPage(Map<String, Object> params, CourseQuery courseQuery) {

        QueryWrapper<CourseEntity> wrapper = new QueryWrapper<>();

        if (courseQuery != null) {
            String name = courseQuery.getTitle();
            String status = courseQuery.getStatus();

            BigDecimal lowerPrice = courseQuery.getLowerPrice();
            BigDecimal higherPrice = courseQuery.getHigherPrice();


            Long buyCount = courseQuery.getBuyCount();

            String subjectId = courseQuery.getSubjectId();
            String subjectParentId = courseQuery.getSubjectParentId();


            if (!StringUtils.isEmpty(name)) {
                wrapper.eq("title", name);
            }
            if (!StringUtils.isEmpty(status)) {
                wrapper.eq("status", status);
            }

            if (!StringUtils.isEmpty(subjectId)) {
                wrapper.eq("subject_id", subjectId);
            }
            if (!StringUtils.isEmpty(subjectParentId)) {
                wrapper.eq("subject_parent_id", subjectParentId);
            }

            if (!StringUtils.isEmpty(lowerPrice)) {
                wrapper.ge("price", lowerPrice);
            }
            if (!StringUtils.isEmpty(higherPrice)) {
                wrapper.le("price", higherPrice);
            }

            if (!StringUtils.isEmpty(buyCount)) {
                wrapper.orderByDesc("buy_count");
            } else if (!StringUtils.isEmpty(courseQuery.getUpdateTime())) {
                wrapper.orderByDesc("update_time");
            } else if (!StringUtils.isEmpty(courseQuery.getPrice())) {
                wrapper.orderByDesc("price");
            } else if (!StringUtils.isEmpty(courseQuery.getTime())) {
                wrapper.orderByDesc("update_time");
            }
        }


        IPage<CourseEntity> page = this.page(
                new Query<CourseEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

         /*
            将courseInfoVo转换为courseEntity和courseDescriptionEntity分别存储到数据库中
         */
        BeanUtils.copyProperties(courseInfoVo, courseEntity);
        BeanUtils.copyProperties(courseInfoVo, courseDescriptionEntity);
        //向课程表中添加课程的基本信息
        int insert = baseMapper.insert(courseEntity);
        if (insert <= 0) {
            throw new GotostudyException(20001, "添加课程信息失败");
        }
        //得到插入课程表时生成的id值，赋值给插入课程简介表时的id，实现一对一的效果
        String courseId = courseEntity.getId();

        //向课程简介表中添加课程的简介信息
        courseDescriptionEntity.setId(courseId);
        courseDescriptionService.save(courseDescriptionEntity);

        return courseId;

    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //根据课程id值查询课程表
        CourseEntity courseEntity = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(courseEntity, courseInfoVo);
        //根据课程id值查询课程描述表
        CourseDescriptionEntity courseDescription = courseDescriptionService.getById(courseId);
        if (!StringUtils.isEmpty(courseDescription)){
            BeanUtils.copyProperties(courseDescription, courseInfoVo);
        }
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {

        BeanUtils.copyProperties(courseInfoVo, courseEntity);
        int update = baseMapper.updateById(courseEntity);
        if (update == 0) {
            throw new GotostudyException(20001, "修改课程信息失败");
        }
        BeanUtils.copyProperties(courseInfoVo, courseDescriptionEntity);
        boolean update1 = courseDescriptionService.updateById(courseDescriptionEntity);
        if (!update1) {
            throw new GotostudyException(20001, "修改课程描述信息失败");
        }

    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {

        return baseMapper.getCoursePublishInfo(courseId);
    }

    @Override
    public R deleteCourseById(String courseId) {

        //根据课程id删除课程小节，连同小节里面的视频也一起删除了
        R r = videoService.removeVideoByCourseId(courseId);

        //根据课程id删除课程章节
        chapterService.removeChapterByCourseId(courseId);

        //根据课程id删除课程描述
        courseDescriptionService.removeById(courseId);//描述表和课程表是一对一的关系

        //根据课程id删除课程本身
        baseMapper.deleteById(courseId);

        return r;
    }

    @Override
    @Cacheable(value = "course", key = "'selectHotCourse'")
    public List<CourseEntity> queryHotCourse() {

        //查询前8条热门课程
        QueryWrapper<CourseEntity> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count");
        wrapper.last("limit 8");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<CourseEntity> getByTeacherId(String id) {
        QueryWrapper<CourseEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public CourseInfoVo getAllInfoById(String id) {

        return baseMapper.getAllInfoById(id);
    }

    @Override
    public CourseInfoVo getBaseCourseInfo(String courseId) {

        return baseMapper.getAllInfoById(courseId);
    }

}