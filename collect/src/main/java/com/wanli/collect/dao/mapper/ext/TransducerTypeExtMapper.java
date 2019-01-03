package com.wanli.collect.dao.mapper.ext;

import com.wanli.collect.model.entity.TransducerType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TransducerTypeExtMapper extends com.wanli.collect.dao.mapper.TransducerTypeMapper {

    /**
     * 获取所有传感器类型
     * @return
     */
    List<TransducerType> listTransducerType();

    /**
     * 根据传感器类型获取传感器
     * @param transducerType
     * @return
     */
    TransducerType findByType(@Param("transducerType") String transducerType);
}