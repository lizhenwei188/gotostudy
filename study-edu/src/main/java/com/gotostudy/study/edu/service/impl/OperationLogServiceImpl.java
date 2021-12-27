package com.gotostudy.study.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.com.utils.mybatisplus.Query;
import com.gotostudy.study.edu.dao.OperationLogDao;
import com.gotostudy.study.edu.entity.OperationLogEntity;
import com.gotostudy.study.edu.service.OperationLogService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("operationLogService")
public class OperationLogServiceImpl extends ServiceImpl<OperationLogDao, OperationLogEntity> implements OperationLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OperationLogEntity> page = this.page(
                new Query<OperationLogEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

}