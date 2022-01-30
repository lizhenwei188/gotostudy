package com.gotostudy.study.usr.dao;

import com.gotostudy.study.usr.entity.UcenterMemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 会员表
 * 
 * @author lizhenwei
 * @email lizhenwei188@foxmail.com
 * @date 2022-01-24 15:15:17
 */
@Mapper
public interface UcenterMemberDao extends BaseMapper<UcenterMemberEntity> {

    Integer countRegisterDay(@Param("day") String day);
}
