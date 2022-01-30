package com.gotostudy.study.edu.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 课程视频
 * 
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-18 11:01:24
 */
@Data
@TableName("video")
public class VideoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 视频ID
	 */
	@TableId
	private String id;
	/**
	 * 课程ID
	 */
	private String courseId;
	/**
	 * 章节ID
	 */
	private String chapterId;
	/**
	 * 节点名称
	 */
	private String title;
	/**
	 * 云端视频资源
	 */
	private String videoSourceId;
	/**
	 * 原始文件名称
	 */
	private String videoOriginalName;
	/**
	 * 排序字段
	 */
	private Integer sort;
	/**
	 * 播放次数
	 */
	private Long playCount;
	/**
	 * 是否可以试听： 0免费 1收费
	 */
	private Integer isFree;
	/**
	 * 视频时长（秒）
	 */
	private Float duration;
	/**
	 * Empty未上传 Transcoding转码中  Normal正常
	 */
	private String status;
	/**
	 * 视频源文件大小（字节）
	 */
	private Long size;
	/**
	 * 乐观锁
	 */
	private Long version;
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
