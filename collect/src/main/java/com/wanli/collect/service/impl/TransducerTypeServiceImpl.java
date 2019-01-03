package com.wanli.collect.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.wanli.collect.context.RequestContext;
import com.wanli.collect.dao.mapper.ext.TransducerTypeExtMapper;
import com.wanli.collect.exception.BaseErrorCode;
import com.wanli.collect.exception.ServiceException;
import com.wanli.collect.model.constants.UserStatusType;
import com.wanli.collect.model.entity.TransducerType;
import com.wanli.collect.model.entity.User;
import com.wanli.collect.service.TransducerTypeService;

/**
 * @author Hu
 * @date 2019/1/3 14:22
 */

@Service
public class TransducerTypeServiceImpl implements TransducerTypeService {

    private final TransducerTypeExtMapper transducerTypeExtMapper;

    public TransducerTypeServiceImpl(TransducerTypeExtMapper transducerTypeExtMapper) {
        this.transducerTypeExtMapper = transducerTypeExtMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Object listTransducerType() {

        User user = RequestContext.getUserInfo();
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        return transducerTypeExtMapper.listTransducerType();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object saveTransducerType(TransducerType transducerType) {

        User user = RequestContext.getUserInfo();
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        if(StringUtils.isEmpty(transducerType.getTransducerType())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(StringUtils.isEmpty(transducerType.getTransducerUnit())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        TransducerType transducerTypeInfo = transducerTypeExtMapper.findByType(transducerType.getTransducerType());
        if(transducerTypeInfo != null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        transducerTypeExtMapper.insert(transducerType);
        return null;
    }

    @Override
    public Object updateTransducerType(Long id, TransducerType transducerType) {

        User user = RequestContext.getUserInfo();
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }
        if(StringUtils.isEmpty(transducerType.getTransducerUnit())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        TransducerType transducerTypeInfo = transducerTypeExtMapper.selectByPrimaryKey(id);
        if(transducerTypeInfo == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        transducerTypeInfo.setTransducerUnit(transducerType.getTransducerUnit());
        transducerTypeExtMapper.updateByPrimaryKeySelective(transducerTypeInfo);
        return null;
    }

    @Override
    public Object removeTransducerType(Long id) {

        User user = RequestContext.getUserInfo();
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }
        TransducerType transducerTypeInfo = transducerTypeExtMapper.selectByPrimaryKey(id);
        if(transducerTypeInfo == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        transducerTypeExtMapper.deleteByPrimaryKey(id);
        return null;
    }
}
