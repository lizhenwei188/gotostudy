package com.gotostudy.study.edu.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2021-12-16 19:53:56
 */
@Data
@TableName("operation_log")
public class OperationLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 用户账号
	 */
	private String operationAccount;
	/**
	 * 用户姓名
	 */
	private String operationName;
	/**
	 * 操作类型
	 */
	private String operationType;
	/**
	 * 操作一级模块
	 */
	private String operationModelOne;
	/**
	 * 操作二级模块
	 */
	private String operationModelTwo;
	/**
	 * 操作详情
	 */
	private String operationDetail;
	/**
	 * 访问者ip
	 */
	private String operationIp;
	/**
	 * 操作结果
	 */
	private String operationResult;
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
