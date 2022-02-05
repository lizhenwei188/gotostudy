package com.gotostudy.study.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gotostudy.study.com.utils.resultutil.GotostudyException;
import com.gotostudy.study.edu.dao.ChapterDao;
import com.gotostudy.study.edu.entity.ChapterEntity;
import com.gotostudy.study.edu.entity.VideoEntity;
import com.gotostudy.study.edu.service.ChapterService;
import com.gotostudy.study.edu.service.VideoService;
import com.gotostudy.study.edu.vo.ChapterVo;
import com.gotostudy.study.edu.vo.VideoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("chapterService")
public class ChapterServiceImpl extends ServiceImpl<ChapterDao, ChapterEntity> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //根据课程id查询出对应的章节信息
        QueryWrapper<ChapterEntity> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<ChapterEntity> chapterList = baseMapper.selectList(wrapperChapter);


        //new 一个大的ChapterVo集合，用于存放章节信息，章节信息里面包含视频小节信息
        List<ChapterVo> chapterVos = new ArrayList<>();
        //循环遍历chapterList将其转化为chapterVo
        for (ChapterEntity chapter : chapterList) {
            String chapterId = chapter.getId();
            //根据章节编号查询出对应的视频小节信息集合
            QueryWrapper<VideoEntity> wrapperVideo = new QueryWrapper<>();
            wrapperVideo.eq("chapter_id", chapterId);
            wrapperVideo.eq("course_id", courseId);
            List<VideoEntity> videoList = videoService.list(wrapperVideo);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVos.add(chapterVo);
            List<VideoVo> videoVos = new ArrayList<>();
            //循环遍历出视频小节信息，将其转为videoVo
            for (VideoEntity video : videoList) {
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(video, videoVo);
                //将遍历出来的videoVo放入到videoVos集合中
                videoVos.add(videoVo);
            }
            //将videoVos做为子集合放入到chapterVo中来，chapterVo中有其对应的集合属性
            chapterVo.setChildren(videoVos);
        }
        return chapterVos;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterId查询出小节，如果不能够查到则删除chapterId对应的chapter
        QueryWrapper<VideoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);
        boolean flag = true;
        if (count > 0) {
            flag = videoService.remove(wrapper);
        }

        int num = baseMapper.deleteById(chapterId);//如果删除成功num为 1

        if (flag && num > 0) {
            return true;
        }  else {
            throw new GotostudyException(20001,"删除章节出错");
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<ChapterEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}