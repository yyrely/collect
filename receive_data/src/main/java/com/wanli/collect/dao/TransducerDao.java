package com.wanli.collect.dao;

import com.wanli.collect.entity.Transducer;

import java.sql.SQLException;

/**
 * Create By HU
 * Date 2019/1/5 18:53
 */

public interface TransducerDao {
    void updateTransducer(Transducer transducer) throws SQLException;
}
