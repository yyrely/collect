package com.wanli.collect.service.impl;

import com.wanli.collect.constants.Constant;
import com.wanli.collect.dao.TransducerConfDao;
import com.wanli.collect.dao.impl.TransducerConfDaoImpl;
import com.wanli.collect.entity.TransducerConf;
import com.wanli.collect.service.TransducerConfService;
import com.wanli.collect.utils.DruidUtils;
import com.wanli.collect.utils.JsonUtils;
import com.wanli.collect.utils.jedis.JedisClient;
import com.wanli.collect.utils.jedis.JedisClientPool;

/**
 * Create By HU
 * Date 2019/1/5 16:59
 */

public class TransducerConfServiceImpl implements TransducerConfService {

    private JedisClient jedisClient = JedisClientPool.getINSTANCE();
    private TransducerConfDao transducerConfDao = TransducerConfDaoImpl.getINSTANCE();

    //单例
    private TransducerConfServiceImpl() {}
    private static TransducerConfService INSTANCE = new TransducerConfServiceImpl();
    public static TransducerConfService getInstance() { return INSTANCE; }

    @Override
    public TransducerConf getTransducerConf(String boardId, String transducerType, String transducerId) {

        DruidUtils.startTransaction();

        TransducerConf transducerConf = null;
        try {
            //从redis中取传感器配置
            String json = jedisClient.get(Constant.TRANSDUCER_CONF + boardId + ":" + transducerType + ":" + transducerId);
            //将取出的json转为传感器配置对象
            //判读是否为空
            if(json == null || "".equals(json)) {
                //从数据库中取传感器配置对象
                transducerConf = transducerConfDao.getTransducerDataConf(boardId,transducerType,transducerId);
                if(transducerConf == null) {
                    throw new RuntimeException("传感器配置不存在");
                }
                //写入到redis中去
                jedisClient.set(Constant.TRANSDUCER_CONF + boardId + ":" + transducerType + ":" + transducerId, JsonUtils.objectToJson(transducerConf));
                jedisClient.expire(Constant.TRANSDUCER_CONF + boardId + ":" + transducerType + ":" + transducerId, 24*60*60);
            }else {
                transducerConf = JsonUtils.jsonToPojo(json, TransducerConf.class);
            }
            DruidUtils.commit();
        } catch (Exception e) {
            DruidUtils.rollback();
        } finally {
            DruidUtils.close();
        }
        //返回结果
        return transducerConf;
    }
}
