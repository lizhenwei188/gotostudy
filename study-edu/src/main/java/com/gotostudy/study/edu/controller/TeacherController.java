package com.gotostudy.study.edu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.edu.entity.TeacherEntity;
import com.gotostudy.study.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




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
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = teacherService.queryPage(params);
        HashMap<String, Object> map = new HashMap<>();
        map.put("totalCount",page.getTotalCount());
        map.put("list",page.getList());
        return R.ok().data(map);
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
