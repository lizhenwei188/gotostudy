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
 * 支付日志表
 * 
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-27 17:47:40
 */
@Data
@TableName("pay_log")
public class PayLogEntity implements Serializable {
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
	 * 支付完成时间
	 */
	private Date payTime;
	/**
	 * 支付金额（分）
	 */
	private BigDecimal totalFee;
	/**
	 * 交易流水号
	 */
	private String transactionId;
	/**
	 * 交易状态
	 */
	private String tradeState;
	/**
	 * 支付类型（1：微信 2：支付宝）
	 */
	private Integer payType;
	/**
	 * 其他属性
	 */
	private String attr;
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
