package com.wanli.collect.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.wanli.collect.context.RequestContext;
import com.wanli.collect.dao.mapper.ext.TransducerDataConfExtMapper;
import com.wanli.collect.exception.BaseErrorCode;
import com.wanli.collect.exception.ServiceException;
import com.wanli.collect.model.constants.UserStatusType;
import com.wanli.collect.model.domain.TransducerKeyBean;
import com.wanli.collect.model.entity.TransducerDataConf;
import com.wanli.collect.model.entity.User;
import com.wanli.collect.service.TransducerDataConfService;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * @author Hu
 * @date 2018/12/29 10:50
 */

@Service
public class TransducerDataConfServiceImpl implements TransducerDataConfService {

    private final TransducerDataConfExtMapper transducerDataConfExtMapper;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public TransducerDataConfServiceImpl(TransducerDataConfExtMapper transducerDataConfExtMapper, StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
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
    public Object updateTransducerDataConf(Long id, TransducerDataConf transducerDataConf) throws JsonProcessingException {

        User user = RequestContext.getUserInfo();
        if(user.getUserStatus() == UserStatusType.NORMAL) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        if(transducerDataConf.getTransducerMax() == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(transducerDataConf.getTransducerMin() == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(transducerDataConf.getTransducerErrornum() == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(transducerDataConf.getTransducerLevel() == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(transducerDataConf.getTransducerWarntime() == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        TransducerDataConf transducerDataConfInfo = transducerDataConfExtMapper.selectByPrimaryKey(id);
        if(transducerDataConfInfo == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        BeanUtils.copyProperties(transducerDataConf,transducerDataConfInfo);
        transducerDataConfInfo.setUpdateTime(Instant.now());
        transducerDataConfExtMapper.updateByPrimaryKeySelective(transducerDataConfInfo);

        //更新Redis中配置信息
        String key = transducerDataConfInfo.getBoardId()+ ":" +transducerDataConfInfo.getTransducerType()+ ":" +transducerDataConfInfo.getTransducerId();
        String value = objectMapper.writeValueAsString(transducerDataConfInfo);
        redisTemplate.opsForHash().put("TransducerDataConf", key, value);

        return null;
    }
}
