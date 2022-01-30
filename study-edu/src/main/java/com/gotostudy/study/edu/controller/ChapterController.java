package com.gotostudy.study.edu.controller;

import com.gotostudy.study.com.utils.resultutil.R;
import com.gotostudy.study.edu.entity.ChapterEntity;
import com.gotostudy.study.edu.service.ChapterService;
import com.gotostudy.study.edu.vo.ChapterVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * 课程
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-18 11:01:24
 */
//@CrossOrigin
@RestController
@RequestMapping("edu/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    //根据课程id查询出对应的课程章节和video
    @ApiOperation("根据课程id值查询对应的章节和视频信息")
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideoByCourseId(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("list", list);
    }

    //添加章节信息
    @ApiOperation("添加章节的章节信息")
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody ChapterEntity chapter) {
        chapterService.save(chapter);
        return R.ok();
    }

    //修改章节的信息，先查询再做修改
    //查询数据
    @ApiOperation("根据章节id查询章节的信息")
    @GetMapping("/queryChapter/{chapterId}")
    public R queryChapter(@PathVariable String chapterId) {
        ChapterEntity chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    //根据章节id值修改章节的信息
    @ApiOperation("根据章节的id值修改对应的章节信息")
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody ChapterEntity chapter) {
        chapterService.updateById(chapter);
        return R.ok();
    }

    //根据章节的id值删除章节信息
    @ApiOperation("根据章节的id删除章节的信息")
    @DeleteMapping("/deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag) {
            return R.ok().data("flag", flag);
        } else {
            return R.error().data("flag", flag);
        }

    }

}
