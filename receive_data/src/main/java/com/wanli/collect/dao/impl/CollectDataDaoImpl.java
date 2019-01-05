package com.wanli.collect.dao.impl;

import com.wanli.collect.dao.CollectDataDao;
import com.wanli.collect.entity.CollectData;
import com.wanli.collect.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

/**
 * Create By HU
 * Date 2019/1/5 18:51
 */

public class CollectDataDaoImpl implements CollectDataDao {

    private CollectDataDaoImpl(){}
    private static CollectDataDao INSTANCE = new CollectDataDaoImpl();
    public static CollectDataDao getINSTANCE(){ return INSTANCE;}

    @Override
    public void addData(CollectData data) throws SQLException {
        QueryRunner qr = new QueryRunner();
        qr.update(DruidUtils.getConnection(),"insert into data(board_id,transducer_id,transducer_type,transducer_data,data_time) values(?,?,?,?,?)",data.getBoardId(),data.getTransducerId(),data.getTransducerType(),data.getTransducerData(),data.getDataTime());
    }
}
