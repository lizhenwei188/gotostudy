package com.gotostudy.study.edu.controller;

import com.gotostudy.study.com.orderVo.CourseWebVoOrder;
import com.gotostudy.study.com.utils.JwtUtils;
import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.com.utils.resultutil.GotostudyException;
import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.edu.constant.CourseStatus;
import com.gotostudy.study.edu.entity.CourseEntity;
import com.gotostudy.study.edu.openfeign.OrdFeign;
import com.gotostudy.study.edu.service.ChapterService;
import com.gotostudy.study.edu.service.CourseService;
import com.gotostudy.study.edu.vo.ChapterVo;
import com.gotostudy.study.edu.vo.CourseInfoVo;
import com.gotostudy.study.edu.vo.CoursePublishVo;
import com.gotostudy.study.edu.vo.CourseQuery;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 课程
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-18 11:01:24
 */
//@CrossOrigin
@RestController
@RequestMapping("edu/course")
public class CourseController {
    @Autowired
    private OrdFeign ordFeign;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;


    //显示课程列表
    @PostMapping("/list/{page}/{limit}")
    @ApiOperation("多条件分页查询所有的课程列表")
    public R list(@PathVariable(value = "page") Integer page,
                  @PathVariable(value = "limit") Integer limit,
                  @RequestBody(required = false) CourseQuery courseQuery) {

        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", limit);
        PageUtils pages = courseService.queryPage(map, courseQuery);
        map.clear();

        map.put("totalCount",pages.getTotalCount());
        map.put("list",pages.getList());

        return R.ok().data(map);
    }

    //添加课程基本信息
    @PostMapping("/saveCourseInfo")
    @ApiOperation("添加课程基本信息")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //返回课程的id为了后面添加课程大纲使用
        String courseId = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",courseId);
    }

    //根据课程id查询出课程的基本信息，用做编辑课程信息时回显数据用
    @GetMapping("/getCourseInfo/{courseId}")
    @ApiOperation("查询出课程基本信息")
    public R getCourseInfoById(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfo",courseInfoVo);
    }

    //数据回显以后修改数据用
    @PostMapping("/updateCourseInfo")
    @ApiOperation("修改课程的基本信息")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程的id值查询最后的课程确认信息
    @GetMapping("/getPublishCourseInfo/{courseId}")
    @ApiOperation("根据课程的id查询最后的确认信息")
    public R getPublishCourseInfo(@PathVariable String courseId) {
        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(courseId);
        return R.ok().data("coursePublish",coursePublishVo);
    }

    //课程的最终发布，也可以用上面的课程修改接口，为了明显再写一个接口，修改课程的状态status为Normal
    @PostMapping("/publishCourse/{courseId}")
    @ApiOperation("根据课程的id实现课程的最终发布")
    public R publishCourse(@PathVariable String courseId) {
        CourseEntity course = new CourseEntity();
        course.setStatus(CourseStatus.NORMAL);
        course.setId(courseId);
        courseService.updateById(course);
        return R.ok();
    }

    //在课程列表页面根据课程的id删除对应的所有课程信息
    @DeleteMapping("/deleteCourse/{courseId}")
    @ApiOperation("根据课程id删除对应的所有课程信息")
    @Transactional
    public R deleteCourse(@PathVariable String courseId) {
        R r = courseService.deleteCourseById(courseId);

        return R.ok();
    }

    // 根据讲师id 查询出所讲的课程
    @GetMapping("/byTeacherId/{id}")
    public R getCourseByTeacherId(@PathVariable String id) {
        List<CourseEntity> courseList = courseService.getByTeacherId(id);

        return R.ok().data("courseList",courseList);
    }

    // 根据讲师id 查询出所讲的课程
    @GetMapping("/allInfo/{id}")
    public R getCourseAllInfo(@PathVariable String id, HttpServletRequest request) {
        CourseInfoVo courseInfo = courseService.getAllInfoById(id);

        List<ChapterVo> chapterList = chapterService.getChapterVideoByCourseId(id);

        String memberId = JwtUtils.getUsrIdByRequest(request);


        if (memberId == null) {
            throw new GotostudyException(20001,"请先进行登录哦!");
        } else {
            //根据课程id和用户id查询订单的支付状态,做为order的消费者
            boolean isbuy = ordFeign.getOrderPayStatus(id, memberId);
            return R.ok().data("courseInfo",courseInfo).data("chapterList",chapterList).data("isbuy", isbuy);
        }
    }

    //根据课程的id查询课程的相关信息，用户后面支付微服务的调用
    @PostMapping("/getCourseInfoOrder/{courseId}")
    @ApiOperation("根据课程的id查询课程的详细信息，用于后面的支付微服务")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getBaseCourseInfo(courseId);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfoVo, courseWebVoOrder);

        return courseWebVoOrder;
    }

}
