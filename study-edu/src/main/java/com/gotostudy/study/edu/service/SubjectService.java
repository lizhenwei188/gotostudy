package com.gotostudy.study.edu.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gotostudy.study.edu.entity.SubjectEntity;
import com.gotostudy.study.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 课程科目
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-17 15:49:39
 */
public interface SubjectService extends IService<SubjectEntity> {

    void saveSubject(MultipartFile file, SubjectService subjectService);


    //课程列表，树形结构
    List<OneSubject> getAllOneTwoSubject();

}

