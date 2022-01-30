package com.gotostudy.study.edu.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: 53Hertz
 **/
@Data
@ApiModel(value = "Course查询对象", description = "课程查询对象封装")
public class CourseQuery {
    private static final long serialVersionUID = 1L;

    /**
     * 课程讲师ID
     */
    private String teacherId;
    /**
     * 课程专业ID
     */
    private String subjectId;
    /**
     *
     */
    private String subjectParentId;
    /**
     * 课程标题
     */
    private String title;

    /**
     * 课程销售价格，设置为0则可免费观看
     */
    private BigDecimal price;
    /**
     * 课程销售价格，设置为0则可免费观看
     */
    private BigDecimal lowerPrice;
    /**
     * 课程销售价格，设置为0则可免费观看
     */
    private BigDecimal higherPrice;
    /**
     * 销售数量
     */
    private Long buyCount;
    /**
     * 浏览数量
     */
    private Long viewCount;
    /**
     * 课程状态 Draft未发布  Normal已发布
     */
    private String status;
    /**
     * 更新时间
     */
    private Date updateTime;



    private String time;
}
