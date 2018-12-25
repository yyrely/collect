package com.wanli.collect.dao.mapper.ext;

import com.wanli.collect.model.entity.Application;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ApplicationExtMapper extends com.wanli.collect.dao.mapper.ApplicationMapper {

    /**
     * 根据应用标识查找应用
     * @param applicationFlag
     * @return
     */
    Application findApplicationByFlag(
            @Param("applicationFlag") String applicationFlag);
}