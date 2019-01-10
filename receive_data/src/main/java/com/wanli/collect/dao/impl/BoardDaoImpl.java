package com.wanli.collect.dao.impl;

import com.wanli.collect.dao.BoardDao;
import com.wanli.collect.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Create By HU
 * Date 2019/1/5 18:48
 */

public class BoardDaoImpl implements BoardDao {

    private BoardDaoImpl(){}
    private static BoardDao INSTANCE = new BoardDaoImpl();
    public static BoardDao getINSTANCE(){ return INSTANCE;}

    @Override
    public void updateBoard(String boardId, byte status, LocalDateTime date) throws SQLException {
        QueryRunner query = new QueryRunner();
        String sql = "update board set" +
                " board_status = ?," +
                "board_time = ? " +
                "where board_id = ?";
        query.update(DruidUtils.getConnection(),sql,status, date, boardId);
    }

}
