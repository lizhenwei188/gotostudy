package com.gotostudy.study.sta.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.baomidou.mybatisplus.annotation.FieldFill;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 网站统计日数据
 * 
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-29 10:43:35
 */
@Data
@TableName("statistics_daily")
public class StatisticsDailyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 统计日期
	 */
	private String dateCalculated;
	/**
	 * 注册人数
	 */
	private Integer registerNum;
	/**
	 * 登录人数
	 */
	private Integer loginNum;
	/**
	 * 每日播放视频数
	 */
	private Integer videoViewNum;
	/**
	 * 每日新增课程数
	 */
	private Integer courseNum;
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
