package com.gotostudy.study.edu.controller;

import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.edu.entity.subject.OneSubject;
import com.gotostudy.study.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 课程科目
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-17 15:49:39
 */
//@CrossOrigin
@RestController
@RequestMapping("edu/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(MultipartFile file){
        subjectService.saveSubject(file,subjectService);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{id}")
    public R delete(@PathVariable(value = "id") Long id){
		subjectService.removeById(id);

        return R.ok();
    }

    //课程分类列表（树形结构显示）
    @GetMapping("/get")
    public R getAllSubject() {
        //泛型为一级分类，一级分类中有它本身，也包含二级分类，既包含所有的数据
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list", list);
    }

}
