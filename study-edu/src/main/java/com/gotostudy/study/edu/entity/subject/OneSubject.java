package com.gotostudy.study.edu.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 一级分类对应的实体类
 * @author: 53Hertz
 **/

@Data
public class OneSubject {

    private String id;

    private String title;

    //在此表示出一个一级分类中有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();

}
