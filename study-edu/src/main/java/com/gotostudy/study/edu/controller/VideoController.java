package com.gotostudy.study.edu.controller;

import com.gotostudy.study.com.utils.resultutil.GotostudyException;
import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.edu.entity.VideoEntity;
import com.gotostudy.study.edu.openfeign.VodFeign;
import com.gotostudy.study.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;



/**
 * 课程视频
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-18 11:01:24
 */
//@CrossOrigin
@RestController
@RequestMapping("edu/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private VodFeign vodFeign;
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
		VideoEntity video = videoService.getById(id);

        return R.ok().data("video", video);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody VideoEntity video){
		videoService.save(video);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody VideoEntity video){
		videoService.updateById(video);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") String id){


        //删除本地数据库中的视频记录videoId为小节的Id并不是视频的Id视频
        //的id为阿里云上的那个视频id现在要根据小节id得到视频id

        //根据id得到对象，根据对象的get方法得到视频id
        VideoEntity videoEntity = videoService.getById(id);
        String videoSourceId = videoEntity.getVideoSourceId();
        //小节里面可能没有视频，所以videoSourceId可能为空
        if (!StringUtils.isEmpty(videoSourceId)) {
            //实现远程删除阿里云视频操作
            R result = vodFeign.deleteAliYunVideoById(videoSourceId);
            if (result.getCode() == 20001) {
                throw new GotostudyException(20001, "删除视频失败，熔断器方法执行");
            }
        }
        //一定要先删除视频再删除小节，当先删除小节以后视频的id也就不存在了
        videoService.removeById(id);
        return R.ok();
    }

}
