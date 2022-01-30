package com.gotostudy.study.ord.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.FieldFill;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 订单
 * 
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-28 12:44:29
 */
@Data
@TableName("order_info")
public class OrderInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 课程id
	 */
	private String courseId;
	/**
	 * 课程名称
	 */
	private String courseTitle;
	/**
	 * 课程封面
	 */
	private String courseCover;
	/**
	 * 讲师名称
	 */
	private String teacherName;
	/**
	 * 会员id
	 */
	private String memberId;
	/**
	 * 会员昵称
	 */
	private String nickname;
	/**
	 * 会员手机
	 */
	private String mobile;
	/**
	 * 会员邮箱
	 */
	private String email;
	/**
	 * 订单金额（分）
	 */
	private BigDecimal totalFee;
	/**
	 * 支付类型（1：微信 2：支付宝）
	 */
	private Integer payType;
	/**
	 * 订单状态（0：未支付 1：已支付）
	 */
	private Integer status;
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
