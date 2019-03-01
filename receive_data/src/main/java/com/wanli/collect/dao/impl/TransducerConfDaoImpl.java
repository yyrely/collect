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
        String sql = "SELECT\n" +
                "\tid,\n" +
                "\tapplication_flag applicationFlag,\n" +
                "\tboard_id boardId,\n" +
                "\ttransducer_type transducerType,\n" +
                "\ttransducer_id transducerId,\n" +
                "\ttransducer_max transducerMax,\n" +
                "\ttransducer_min transducerMin,\n" +
                "\ttransducer_errornum transducerErrornum,\n" +
                "\ttransducer_level transducerLevel,\n" +
                "\ttransducer_warntime transducerWarntime,\n" +
                "\tconf_description confDescription \n" +
                "FROM\n" +
                "\ttransducer_data_conf \n" +
                "WHERE\n" +
                "\tboard_id = ?\n" +
                "\tAND transducer_type = ?\n" +
                "\tAND transducer_id = ?;";
        return query.query(DruidUtils.getConnection(), sql, new BeanHandler<>(TransducerConf.class),boardId,transducerType,transducerId);
    }
}
