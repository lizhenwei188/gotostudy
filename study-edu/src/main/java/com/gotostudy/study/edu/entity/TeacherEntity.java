package com.gotostudy.study.edu.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 讲师
 * 
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-11 15:50:21
 */
@Data
@TableName("teacher")
public class TeacherEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 讲师ID
	 */
	@TableId
	private String id;
	/**
	 * 讲师姓名
	 */
	private String name;
	/**
	 * 讲师简介
	 */
	private String intro;
	/**
	 * 讲师资历,一句话说明讲师
	 */
	private String career;
	/**
	 * 头衔 1高级讲师 2首席讲师
	 */
	private Integer level;
	/**
	 * 讲师头像
	 */
	private String avatar;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 逻辑删除 1（true）已删除， 0（false）未删除
	 */
	private Integer isDeleted;
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
