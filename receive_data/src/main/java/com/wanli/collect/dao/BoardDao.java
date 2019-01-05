package com.wanli.collect.dao;

import java.sql.SQLException;
import java.time.Instant;

/**
 * Create By HU
 * Date 2019/1/5 18:48
 */

public interface BoardDao {
    void updateBoard(String boardId, int status, Instant now) throws SQLException;
}
