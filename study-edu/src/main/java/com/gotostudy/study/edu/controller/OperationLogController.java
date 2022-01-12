package com.gotostudy.study.edu.controller;

import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.com.utils.ResultUtil.R;
import com.gotostudy.study.edu.entity.OperationLogEntity;
import com.gotostudy.study.edu.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2021-12-16 19:53:56
 */
@RestController
@RequestMapping("edu/operationlog")
public class OperationLogController {
    @Autowired
    private OperationLogService operationLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = operationLogService.queryPage(params);

        return R.ok().data("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		OperationLogEntity operationLog = operationLogService.getById(id);

        return R.ok().data("operationLog", operationLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody OperationLogEntity operationLog){
		operationLogService.save(operationLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody OperationLogEntity operationLog){
		operationLogService.updateById(operationLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		operationLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
