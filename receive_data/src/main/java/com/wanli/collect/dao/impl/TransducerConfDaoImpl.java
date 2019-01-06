package com.wanli.collect.dao.impl;

import com.wanli.collect.dao.TransducerConfDao;
import com.wanli.collect.entity.TransducerConf;
import com.wanli.collect.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * Create By HU
 * Date 2019/1/5 18:54
 */

public class TransducerConfDaoImpl implements TransducerConfDao {

    private TransducerConfDaoImpl(){}
    private static TransducerConfDao INSTANCE = new TransducerConfDaoImpl();
    public static TransducerConfDao getINSTANCE(){ return INSTANCE;}

    @Override
    public TransducerConf getTransducerDataConf(String boardId, String transducerType, String transducerId) throws SQLException {
        QueryRunner query = new QueryRunner();
        String sql = "select " +
                "application_flag applicationFlag," +
                "transducer_max transducerMax," +
                "transducer_min transducerMin," +
                "transducer_errornum transducerErrornum," +
                "transducer_level transducerLevel," +
                "transducer_warntime transducerWarntime " +
                "from transducer_data_conf " +
                "where board_id = ? " +
                "and transducer_type = ? " +
                "and transducer_id = ?";
        return query.query(DruidUtils.getConnection(), sql, new BeanHandler<>(TransducerConf.class),boardId,transducerType,transducerId);
    }
}
