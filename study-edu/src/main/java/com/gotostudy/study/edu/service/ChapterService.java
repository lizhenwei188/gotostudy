package com.gotostudy.study.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.edu.entity.ChapterEntity;
import com.gotostudy.study.edu.vo.ChapterVo;

import java.util.List;
import java.util.Map;

/**
 * 课程
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-18 11:01:24
 */
public interface ChapterService extends IService<ChapterEntity> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}

