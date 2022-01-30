package com.gotostudy.study.edu.entity.subject;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @description: 课程分类
 * @author: 53Hertz
 **/
@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
