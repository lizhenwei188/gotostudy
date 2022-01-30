package com.gotostudy.study.edu.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @description: 视频小节对应的实体类
 * @author: 53Hertz
 **/

@Data
@Component
public class VideoVo {
    private String id;

    private String title;

    private String videoSourceId;
}
