package com.gotostudy.study.edu.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 课程
 * 
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-18 11:01:24
 */
@Data
@Component
@TableName("course")
public class CourseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 课程ID
	 */
	@TableId
	private String id;
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
	 * 总课时
	 */
	private Integer lessonNum;
	/**
	 * 课程封面图片路径
	 */
	private String cover;
	/**
	 * 销售数量
	 */
	private Long buyCount;
	/**
	 * 浏览数量
	 */
	private Long viewCount;
	/**
	 * 乐观锁
	 */
	private Long version;
	/**
	 * 课程状态 Draft未发布  Normal已发布
	 */
	private String status;
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
