package com.wanli.collect.service;

import com.wanli.collect.model.domain.TransducerKeyBean;

/**
 * @author Hu
 * @date 2018/12/29 13:52
 */

public interface CollectDataService {

    /**
     * 获取传感器数据列表
     * @param transducerKeyBean
     * @param page
     * @param size
     * @return
     */
    Object listData(TransducerKeyBean transducerKeyBean, Integer page, Integer size);
}
