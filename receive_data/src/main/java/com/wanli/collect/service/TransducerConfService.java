package com.wanli.collect.service;

import com.wanli.collect.entity.TransducerConf;

/**
 * Create By HU
 * Date 2019/1/5 16:59
 */

public interface TransducerConfService {

    TransducerConf getTransducerConf(String boardId, String analysisType, String transducerId);
}
