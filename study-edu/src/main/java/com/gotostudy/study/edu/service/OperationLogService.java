package com.gotostudy.study.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.edu.entity.OperationLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2021-12-16 19:53:56
 */
public interface OperationLogService extends IService<OperationLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

