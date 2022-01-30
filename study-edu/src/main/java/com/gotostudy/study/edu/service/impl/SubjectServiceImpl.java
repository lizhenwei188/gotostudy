package com.gotostudy.study.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gotostudy.study.edu.dao.SubjectDao;
import com.gotostudy.study.edu.entity.SubjectEntity;
import com.gotostudy.study.edu.entity.subject.OneSubject;
import com.gotostudy.study.edu.entity.subject.SubjectData;
import com.gotostudy.study.edu.entity.subject.TwoSubject;
import com.gotostudy.study.edu.listener.SubjectExcelListener;
import com.gotostudy.study.edu.service.SubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Service("subjectService")
public class SubjectServiceImpl extends ServiceImpl<SubjectDao, SubjectEntity> implements SubjectService {

    @Override//添加课程分类
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            //文件输入流
            InputStream inputStream = file.getInputStream();//相当于把数据交给监听器，监听器得到具体数据以后
            //再来用这个类中的方法将数据写入到数据库
            // 通过参数的形式给监听器传入一个subjectService
            //监听器中有这么一个属性，通过有参给SubjectExcelListener
            //赋值，属性就可以调用subjectService里面的方法
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {                         //读取文件内容       new SubjectExcelListener
            e.printStackTrace();                          //导致SubjectExcelListener不能交给spring进行管理
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询一级分类 parentid = 0
        QueryWrapper<SubjectEntity> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", 0);//baseMapper为mapper层的对象，继承类中封装好的
        List<SubjectEntity> oneSubjectList = baseMapper.selectList(wrapperOne);//this.list(wrapperOne);这种表示用自己自己来调

        //查询二级分类parentid != 0
        QueryWrapper<SubjectEntity> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", 0);//baseMapper为mapper层的对象，继承类中封装好的
        List<SubjectEntity> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建一个集合用于最终数据的封装,把一级分类装进入，然后再把二级分类装进一级分类
        //需要的数据时oneSubject和twoSubject而不是EduSubject所以需要将其遍历然后封装进finallySubject中
        List<OneSubject> finallySubject = new ArrayList<>();

        //封装一级分类，把一级分类中的数据遍历出来，然后装进finallySubject中
        for (SubjectEntity eduOneSubject : oneSubjectList) {
            //将eduSubject中的值获取出来，放到oneSubject中然后再放入到finallySubject中

            //1.手动实现的方法,当有多个属性时不适合使用
            OneSubject oneSubject = new OneSubject();

            //2.spring框架中封装好的工具类
            BeanUtils.copyProperties(eduOneSubject, oneSubject);

            finallySubject.add(oneSubject);

            //封装二级分类，把二级分类遍历出来，放入到一级分类中的二级分类的集合中（一级分类中有这么一个属性集合）
            //在一级分类中嵌套一个二级循环
            List<TwoSubject> twoFinallySubjects = new ArrayList<>();
            for (SubjectEntity eduTwoSubject : twoSubjectList) {
                //判断二级分类的parentid是否和一级分类的id一致
                if (eduTwoSubject.getParentId().equals(eduOneSubject.getId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduTwoSubject, twoSubject);
                    twoFinallySubjects.add(twoSubject);
                }
            }
            //把所有的二级分类集合放到对应的一级分类中
            oneSubject.setChildren(twoFinallySubjects);
        }
        return finallySubject;
    }
}