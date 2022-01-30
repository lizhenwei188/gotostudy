package com.gotostudy.study.edu.controller;


import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.edu.entity.CourseEntity;
import com.gotostudy.study.edu.entity.TeacherEntity;
import com.gotostudy.study.edu.service.CourseService;
import com.gotostudy.study.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 前台展示页面的内容
 * @author: 53Hertz
 **/
//@CrossOrigin
@RestController
@Api(description = "查询前八门课程和前四名讲师")
@RequestMapping("/edu/index")
//查询前八门课程和前四名讲师
public class IndexFrontController {


    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;

    @GetMapping("/index")
    @ApiOperation("查询前8条热门课程和前4条讲师")
    public R index() {

        //查询前8条热门课程
        List<CourseEntity> courseList = courseService.queryHotCourse();

        //查询前4条名师
        List<TeacherEntity> teacherList = teacherService.queryHotTeacher();

        return R.ok().data("courseList", courseList).data("teacherList", teacherList);
    }

}
