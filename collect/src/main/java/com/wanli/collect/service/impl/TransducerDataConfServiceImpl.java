package com.wanli.collect.service.impl;

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

    public TransducerDataConfServiceImpl(TransducerDataConfExtMapper transducerDataConfExtMapper) {
        this.transducerDataConfExtMapper = transducerDataConfExtMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Object findTransducerDataConf(TransducerKeyBean transducerKeyBean) {
        return transducerDataConfExtMapper.findTransducerDataConf(transducerKeyBean);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateTransducerDataConf(Long id, TransducerDataConf transducerDataConf) {

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

        transducerDataConf.setId(id);
        transducerDataConf.setUpdateTime(Instant.now());
        transducerDataConfExtMapper.insertSelective(transducerDataConf);

        return null;
    }
}
