package com.wanli.collect.dao;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Create By HU
 * Date 2019/1/5 18:48
 */

public interface BoardDao {
    void updateBoard(String boardId, byte status, LocalDateTime now) throws SQLException;
}
