package com.gotostudy.study.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.edu.dao.VideoDao;
import com.gotostudy.study.edu.entity.VideoEntity;
import com.gotostudy.study.edu.openfeign.VodFeign;
import com.gotostudy.study.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Service("videoService")
public class VideoServiceImpl extends ServiceImpl<VideoDao, VideoEntity> implements VideoService {
    @Autowired//注入实现远程的调用
    private VodFeign vodFeign;

    @Override
    public R removeVideoByCourseId(String courseId) {
        //删除视频
        //根据课程id查询出所有的视频id，
        QueryWrapper<VideoEntity> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id" , courseId);
        /*
        代码的优化，查询时其实只需要视频的id这一个字段，
        没必要查询出所有的字段，再遍历取视频id
        videoWrapper.select("video_source_id");
        加不加都可，但加入数据库查询效率会高一些
        因为加了表示只查这一个字段
         */
        videoWrapper.select("video_source_id");
        //返回的仍然是VideoEntity，但是里面只有video_source_id这么一个字段
        List<VideoEntity> videoList = baseMapper.selectList(videoWrapper);

        //用于封装阿里云视频id
        List<String> videoSourceIdList = new ArrayList<>();
        for (VideoEntity videoEntity : videoList) {
            String videoSourceId = videoEntity.getVideoSourceId();
            //表中视频的id可能为空
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoSourceIdList.add(videoSourceId);
            }
        }
        R r = null;
        //判断集合是否为空，小节里面可能一个视频也没有
        if (!StringUtils.isEmpty(videoSourceIdList)) {
            r = vodFeign.deleteMultiVideo(videoSourceIdList);
        }
        //删除小节
        QueryWrapper<VideoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id" , courseId);
        baseMapper.delete(wrapper);

        return r;
    }

}