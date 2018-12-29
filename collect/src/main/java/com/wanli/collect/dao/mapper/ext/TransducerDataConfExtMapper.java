package com.wanli.collect.dao.mapper.ext;

import com.wanli.collect.model.domain.TransducerKeyBean;
import com.wanli.collect.model.entity.TransducerDataConf;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TransducerDataConfExtMapper extends com.wanli.collect.dao.mapper.TransducerDataConfMapper {

    /**
     * 获取传感器配置信息
     * @param transducerKeyBean
     * @return
     */
    TransducerDataConf findTransducerDataConf(@Param("transducerKeyBean") TransducerKeyBean transducerKeyBean);
}