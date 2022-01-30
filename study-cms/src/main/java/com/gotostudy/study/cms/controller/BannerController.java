package com.gotostudy.study.cms.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import com.gotostudy.study.cms.entity.BannerEntity;
import com.gotostudy.study.cms.service.BannerService;
import com.gotostudy.study.com.utils.mybatisplus.PageUtils;
import com.gotostudy.study.com.utils.resultutil.R;



/**
 * 首页banner表
 *
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-21 15:55:31
 */
//@CrossOrigin
@RestController
@RequestMapping("cms/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    /**
     * 列表
     */
    // 给到后台管理使用
    @PostMapping("/list/{page}/{limit}")
    public R list(@PathVariable(value = "page") Integer page,
                  @PathVariable(value = "limit") Integer limit) {

        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", limit);
        PageUtils pages = bannerService.queryPage(map);
        map.clear();

        map.put("totalCount",pages.getTotalCount());
        map.put("list",pages.getList());
        return R.ok().data(map);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
		BannerEntity banner = bannerService.getById(id);

        return R.ok().data("banner", banner);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BannerEntity banner){
		bannerService.save(banner);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody BannerEntity banner){
		bannerService.updateById(banner);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody String[] ids){
		bannerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 给前台使用
     */
    @RequestMapping("/info")
    @Cacheable(value = "banner", key = "'selectIndexList'")
    public R allInfo(){
        List<BannerEntity> bannerList = bannerService.getAll();

        return R.ok().data("bannerList", bannerList);
    }

}
