package com.wanli.collect.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wanli.collect.model.domain.TransducerKeyBean;
import com.wanli.collect.model.entity.TransducerDataConf;

/**
 * @author Hu
 * @date 2018/12/29 10:50
 */

public interface TransducerDataConfService {

    /**
     * 获取传感器配置
     * @param transducerKeyBean
     * @return
     */
    Object findTransducerDataConf(TransducerKeyBean transducerKeyBean);

    /**
     * 更新传感器
     * @param id
     * @param transducerDataConf
     * @return
     */
    Object updateTransducerDataConf(Long id, TransducerDataConf transducerDataConf) throws Exception;


    Object updateTransducerDataConfByThree(TransducerDataConf transducerDataConf) throws Exception;
}
