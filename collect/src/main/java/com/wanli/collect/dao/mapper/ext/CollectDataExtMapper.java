package com.wanli.collect.dao.mapper.ext;

import com.wanli.collect.dao.mapper.CollectDataMapper;
import com.wanli.collect.model.domain.TransducerKeyBean;
import com.wanli.collect.model.entity.CollectData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollectDataExtMapper extends CollectDataMapper {

    /**
     * 获取传感器数据列表
     * @param transducerKeyBean
     * @return
     */
    List<CollectData> listData(@Param("transducerKeyBean") TransducerKeyBean transducerKeyBean);
}