package com.wanli.collect.dao.impl;

import com.wanli.collect.dao.TransducerDao;
import com.wanli.collect.entity.Transducer;
import com.wanli.collect.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

/**
 * Create By HU
 * Date 2019/1/5 18:53
 */

public class TransducerDaoImpl implements TransducerDao {

    private TransducerDaoImpl(){}
    private static TransducerDao INSTANCE = new TransducerDaoImpl();
    public static TransducerDao getINSTANCE(){ return INSTANCE;}

    @Override
    public void updateTransducer(Transducer transducer) throws SQLException {
        QueryRunner qr = new QueryRunner();
        String sql = "update transducer " +
                "set transducer_status = ? ," +
                "transducer_nowdata = ? ," +
                "transducer_time = ?" +
                "where board_id = ? " +
                "and transducer_type = ? " +
                "and transducer_id = ?";
        qr.update(DruidUtils.getConnection(), sql,
                transducer.getTransducerStatus(),
                transducer.getTransducerNowdata(),
                transducer.getTransducerTime(),
                transducer.getBoardId(),
                transducer.getTransducerType(),
                transducer.getTransducerId());
    }
}
