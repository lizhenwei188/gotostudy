package com.gotostudy.study.edu.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 课程简介
 * 
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-18 11:01:24
 */
@Data
@Component
@TableName("course_description")
public class CourseDescriptionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 课程ID
	 */
	@TableId(value = "id", type = IdType.INPUT)
	private String id;
	/**
	 * 课程简介
	 */
	private String description;
	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

}
