package com.gotostudy.study.edu.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 课程章节对应的实体类
 * @author: 53Hertz
 **/

@Data
@Component
public class ChapterVo {
    private String id;

    private String title;

    private List<VideoVo> children = new ArrayList<>();
}
