package com.gotostudy.study.edu.controller;

import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.edu.entity.TeacherEntity;
import com.gotostudy.study.edu.service.TeacherService;
import com.gotostudy.study.edu.vo.TeacherQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 讲师
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-11 15:50:21
 */
@RestController
@RequestMapping("edu/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    /**
     * 分页检索列表
     */
    @PostMapping("/list/{page}/{limit}")
    public R list(@PathVariable(value = "page") Integer page,
                  @PathVariable(value = "limit") Integer limit,
                  @RequestBody(required = false) TeacherQuery teacherQuery) {

        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", limit);
        PageUtils pages = teacherService.queryPage(map, teacherQuery);
        map.clear();

        map.put("totalCount",pages.getTotalCount());
        map.put("list",pages.getList());

        return R.ok().data(map);
    }

    @GetMapping("/info/all")
    public R allInfo(){
        List<TeacherEntity> teacherList = teacherService.list();

        return R.ok().data("teacherList", teacherList);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
		TeacherEntity teacher = teacherService.getById(id);

        return R.ok().data("teacher", teacher);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody TeacherEntity teacher){
		teacherService.save(teacher);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TeacherEntity teacher){
		teacherService.updateById(teacher);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody List<Long> ids){
		teacherService.removeByIds(ids);

        return R.ok();
    }

}
