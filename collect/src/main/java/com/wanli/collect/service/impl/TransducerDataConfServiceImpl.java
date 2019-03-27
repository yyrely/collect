package com.wanli.collect.service.impl;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import com.wanli.collect.dao.mapper.ext.BoardExtMapper;
import com.wanli.collect.dao.mapper.ext.TransducerExtMapper;
import com.wanli.collect.model.constants.DataStatusType;
import com.wanli.collect.model.entity.Board;
import com.wanli.collect.model.entity.Transducer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanli.collect.context.RequestContext;
import com.wanli.collect.dao.mapper.ext.TransducerDataConfExtMapper;
import com.wanli.collect.exception.BaseErrorCode;
import com.wanli.collect.exception.ServiceException;
import com.wanli.collect.model.constants.Contants;
import com.wanli.collect.model.constants.UserStatusType;
import com.wanli.collect.model.domain.TransducerKeyBean;
import com.wanli.collect.model.entity.TransducerDataConf;
import com.wanli.collect.model.entity.User;
import com.wanli.collect.service.TransducerDataConfService;

/**
 * @author Hu
 * @date 2018/12/29 10:50
 */

@Service
public class TransducerDataConfServiceImpl implements TransducerDataConfService {

    private final TransducerExtMapper transducerExtMapper;
    private final BoardExtMapper boardExtMapper;
    private final TransducerDataConfExtMapper transducerDataConfExtMapper;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public TransducerDataConfServiceImpl(TransducerExtMapper transducerExtMapper, BoardExtMapper boardExtMapper, TransducerDataConfExtMapper transducerDataConfExtMapper, StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.transducerExtMapper = transducerExtMapper;
        this.boardExtMapper = boardExtMapper;
        this.transducerDataConfExtMapper = transducerDataConfExtMapper;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Object findTransducerDataConf(TransducerKeyBean transducerKeyBean) {
        return transducerDataConfExtMapper.findTransducerDataConf(transducerKeyBean);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateTransducerDataConf(Long id, TransducerDataConf transducerDataConf) throws Exception {

        User user = RequestContext.getUserInfo();
        if (user.getUserStatus() == UserStatusType.NORMAL) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        TransducerDataConf transducerDataConfInfo = transducerDataConfExtMapper.selectByPrimaryKey(id);
        if (transducerDataConfInfo == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if (user.getUserStatus() == UserStatusType.CHARGE && !user.getApplicationFlag().equals(transducerDataConfInfo.getApplicationFlag())) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        if (transducerDataConf.getTransducerMax() != null) {
            transducerDataConfInfo.setTransducerMax(transducerDataConf.getTransducerMax());
        }
        if (transducerDataConf.getTransducerMin() != null) {
            transducerDataConfInfo.setTransducerMin(transducerDataConf.getTransducerMin());
        }
        if (transducerDataConf.getTransducerErrornum() != null) {
            transducerDataConfInfo.setTransducerErrornum(transducerDataConf.getTransducerErrornum());
        }
        if (transducerDataConf.getTransducerLevel() != null) {
            transducerDataConfInfo.setTransducerLevel(transducerDataConf.getTransducerLevel());
        }
        if (transducerDataConf.getTransducerWarntime() != null) {
            transducerDataConfInfo.setTransducerWarntime(transducerDataConf.getTransducerWarntime());
        }
        //更新配置
        transducerDataConfInfo.setUpdateTime(Instant.now());
        transducerDataConfExtMapper.updateByPrimaryKeySelective(transducerDataConfInfo);

        //更新传感器状态
        Transducer transducer = transducerExtMapper.findTransducer(transducerDataConfInfo.getBoardId(), transducerDataConfInfo.getTransducerType(), transducerDataConfInfo.getTransducerId());
        DataStatusType status;
        if (transducer.getTransducerNowdata().compareTo(transducerDataConfInfo.getTransducerMax()) == 1) {
            status = DataStatusType.HIGH;
        } else if (transducer.getTransducerNowdata().compareTo(transducerDataConfInfo.getTransducerMin()) == -1) {
            status = DataStatusType.LOW;
        } else {
            status = DataStatusType.NORMAL;
        }
        transducer.setTransducerStatus(status);
        transducerExtMapper.updateByPrimaryKeySelective(transducer);

        //更新板状态
        Board board = boardExtMapper.selectByPrimaryKey(transducerDataConfInfo.getBoardId());
        board.setBoardStatus(status);
        boardExtMapper.updateByPrimaryKeySelective(board);

        //更新Redis中配置信息
        String key = transducerDataConfInfo.getBoardId() + ":" + transducerDataConfInfo.getTransducerType() + ":" + transducerDataConfInfo.getTransducerId();
        String value = objectMapper.writeValueAsString(transducerDataConfInfo);
        redisTemplate.expire(Contants.TRANSDUCER_CONF + key, 0,TimeUnit.SECONDS);
        //将报警间隔时间置0
        redisTemplate.expire(Contants.TRANSDUCER_WARN_TIME_PRE + key, 0, TimeUnit.SECONDS);
        return null;
    }
}
