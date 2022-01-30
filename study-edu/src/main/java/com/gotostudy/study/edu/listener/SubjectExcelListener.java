package com.gotostudy.study.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gotostudy.study.com.utils.resultutil.GotostudyException;
import com.gotostudy.study.edu.entity.SubjectEntity;
import com.gotostudy.study.edu.entity.subject.SubjectData;
import com.gotostudy.study.edu.service.SubjectService;


import java.util.Map;

/**
 * @description:
 * @author: 53Hertz
 **/

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    //因为SubjectExcelListener不能（在EduSubjectServiceImpl new 了SubjectExcelListener的对象）
    // 交给spring进行管理，所以不能注入其他的对象，得到的数据就不能让mapper存到数据库

    //通过有参和无参构造器注入对象
    public SubjectService subjectService;//为了将读取到的数据用service调用mapper填入数据库中
    public SubjectExcelListener() {}

    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override//获取文件内容，一行一行的读取，循环执行此方法
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {//文件为空
            throw new GotostudyException(20001,"表格文件为空");
        }
        //一行一行的读取数据，第一个值为第一列，第二个值为第二列
        SubjectEntity oneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        //表示数据库中没有这个值，可以向数据库中进行添加操作
        if (oneSubject == null) {
            oneSubject = new SubjectEntity();//oneSubject为空，所以要赋值为一个新对象
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(oneSubject);
        }
        //获取一级分类的id值
        String pid = oneSubject.getId();
        //向数据中添加二级分类
        SubjectEntity twoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if (twoSubject == null) {
            twoSubject = new SubjectEntity();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(twoSubject);
        }
    }

    //判断一级分类不能重复添加
    private SubjectEntity existOneSubject(SubjectService subjectService,String name) {
        QueryWrapper<SubjectEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", 0);
        wrapper.eq("title", name);
        return subjectService.getOne(wrapper);
    }

    //判断二级分类不能重复添加
    private SubjectEntity existTwoSubject(SubjectService subjectService,String name,String pid) {
        QueryWrapper<SubjectEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", pid);
        wrapper.eq("title", name);
        return subjectService.getOne(wrapper);
    }

    @Override//获取文件表头
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
