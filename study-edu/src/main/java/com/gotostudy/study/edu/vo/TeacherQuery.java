package com.gotostudy.study.edu.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @title: TeacherVo
 * @Author 53Hertz
 * @Date: 2022/1/12 11:30
 * @Version 1.0
 */

@Data
public class TeacherQuery {
    /**
     * 讲师姓名
     */
    private String name;
    /**
     * 头衔 1高级讲师 2首席讲师
     */
    private Integer level;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
}
