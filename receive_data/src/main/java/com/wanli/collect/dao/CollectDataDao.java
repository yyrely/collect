package com.wanli.collect.dao;

import com.wanli.collect.entity.CollectData;

import java.sql.SQLException;

/**
 * Create By HU
 * Date 2019/1/5 18:51
 */

public interface CollectDataDao {
    void addData(CollectData data) throws SQLException;
}
