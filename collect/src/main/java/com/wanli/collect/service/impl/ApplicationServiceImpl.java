package com.wanli.collect.service.impl;

import com.wanli.collect.dao.mapper.ext.ApplicationExtMapper;
import com.wanli.collect.exception.BaseErrorCode;
import com.wanli.collect.exception.ServiceException;
import com.wanli.collect.model.entity.Application;
import com.wanli.collect.service.ApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Hu
 * @date 2018/12/25 16:19
 */

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationExtMapper applicationExtMapper;

    public ApplicationServiceImpl(ApplicationExtMapper applicationExtMapper) {
        this.applicationExtMapper = applicationExtMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Object checkApplicationFlag(String applicationFlag) {

        Application application = applicationExtMapper.findApplicationByFlag(applicationFlag);
        if(application != null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        return null;
    }
}
