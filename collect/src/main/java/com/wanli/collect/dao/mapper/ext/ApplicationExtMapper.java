package com.wanli.collect.dao.mapper.ext;

import com.wanli.collect.model.entity.Application;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApplicationExtMapper extends com.wanli.collect.dao.mapper.ApplicationMapper {

    /**
     * 根据应用标识查找应用
     * @param applicationFlag
     * @return
     */
    Application findApplicationByFlag(
            @Param("applicationFlag") String applicationFlag);

    /**
     * 获取应用列表
     * @return
     */
    List<Application> listApplication(@Param("applicationName") String applicationName);

    /**
     * 获取应用标识列表
     * @return
     */
    List<Application> listApplicationFlags();
}