package com.gotostudy.study.edu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @program: guli_parent
 * @description: 课程信息的封装类
 * @author: 53Hertz
 * @create: 2021-03-13 22:01
 **/

//封装从前端传过来的表单，因为entity里的实体类封装的信息不全，课程描述没有封装进去
//所以需要创建这么一个类封装课程的全部信息
@Data
@ApiModel(value = "课程基本信息", description = "编辑课程基本信息的表单对象")
@Component
public class CourseInfoVo {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "二级课程id")
    private String subjectId;

    @ApiModelProperty(value = "一级课程id")
    private String subjectParentId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")//bigdecimal可以使价格更加精准
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "购买量")
    private Integer buyCount;

    @ApiModelProperty(value = "观看量")
    private Integer viewCount;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程简介")
    private String description;



    private String teacherName;
    private String teacherCareer;
    private String teacherAvatar;
    private String subjectName;
    private String subjectParentName;

}
