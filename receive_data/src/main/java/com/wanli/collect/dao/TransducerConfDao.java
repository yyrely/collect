package com.wanli.collect.dao;

import com.wanli.collect.entity.TransducerConf;

import java.sql.SQLException;

/**
 * Create By HU
 * Date 2019/1/5 18:54
 */

public interface TransducerConfDao {
    TransducerConf getTransducerDataConf(String boardId, String transducerType, String transducerId) throws SQLException;
}
