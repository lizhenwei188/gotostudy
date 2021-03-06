package com.gotostudy.study.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.com.utils.mybatisplus.Query;
import com.gotostudy.study.com.utils.resultutil.GotostudyException;
import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.edu.dao.CourseDao;
import com.gotostudy.study.edu.entity.CourseDescriptionEntity;
import com.gotostudy.study.edu.entity.CourseEntity;
import com.gotostudy.study.edu.service.*;
import com.gotostudy.study.edu.vo.CourseInfoVo;
import com.gotostudy.study.edu.vo.CoursePublishVo;
import com.gotostudy.study.edu.vo.CourseQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


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
            ???courseInfoVo?????????courseEntity???courseDescriptionEntity???????????????????????????
         */
        BeanUtils.copyProperties(courseInfoVo, courseEntity);
        BeanUtils.copyProperties(courseInfoVo, courseDescriptionEntity);
        //??????????????????????????????????????????
        int insert = baseMapper.insert(courseEntity);
        if (insert <= 0) {
            throw new GotostudyException(20001, "????????????????????????");
        }
        //?????????????????????????????????id??????????????????????????????????????????id???????????????????????????
        String courseId = courseEntity.getId();

        //????????????????????????????????????????????????
        courseDescriptionEntity.setId(courseId);
        courseDescriptionService.save(courseDescriptionEntity);

        return courseId;

    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //????????????id??????????????????
        CourseEntity courseEntity = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(courseEntity, courseInfoVo);
        //????????????id????????????????????????
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
            throw new GotostudyException(20001, "????????????????????????");
        }
        BeanUtils.copyProperties(courseInfoVo, courseDescriptionEntity);
        boolean update1 = courseDescriptionService.updateById(courseDescriptionEntity);
        if (!update1) {
            throw new GotostudyException(20001, "??????????????????????????????");
        }

    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {

        return baseMapper.getCoursePublishInfo(courseId);
    }

    @Override
    public R deleteCourseById(String courseId) {

        //????????????id??????????????????????????????????????????????????????????????????
        R r = videoService.removeVideoByCourseId(courseId);

        //????????????id??????????????????
        chapterService.removeChapterByCourseId(courseId);

        //????????????id??????????????????
        courseDescriptionService.removeById(courseId);//??????????????????????????????????????????

        //????????????id??????????????????
        baseMapper.deleteById(courseId);

        return r;
    }

    @Override
    @Cacheable(value = "course", key = "'selectHotCourse'")
    public List<CourseEntity> queryHotCourse() {

        //?????????8???????????????
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