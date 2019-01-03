package com.wanli.collect.service;

import com.wanli.collect.model.entity.TransducerType;

/**
 * @author Hu
 * @date 2019/1/3 14:22
 */

public interface TransducerTypeService {

    /**
     * 获取传感器类型列表
     * @return
     */
    Object listTransducerType();

    /**
     * 添加传感器类型
     * @param transducerType
     * @return
     */
    Object saveTransducerType(TransducerType transducerType);

    /**
     * 更新传感器类型
     * @param id
     * @param transducerType
     * @return
     */
    Object updateTransducerType(Long id, TransducerType transducerType);

    /**
     * 移除传感器类型
     * @param id
     * @return
     */
    Object removeTransducerType(Long id);
}
