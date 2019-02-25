package com.wanli.collect.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanli.collect.context.RequestContext;
import com.wanli.collect.dao.mapper.ext.ApplicationExtMapper;
import com.wanli.collect.dao.mapper.ext.BoardExtMapper;
import com.wanli.collect.dao.mapper.ext.UserExtMapper;
import com.wanli.collect.exception.BaseErrorCode;
import com.wanli.collect.exception.ServiceException;
import com.wanli.collect.model.constants.UserStatusType;
import com.wanli.collect.model.entity.Application;
import com.wanli.collect.model.entity.Board;
import com.wanli.collect.model.entity.User;
import com.wanli.collect.service.ApplicationService;
import com.wanli.collect.service.BoardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author Hu
 * @date 2018/12/25 16:19
 */

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationExtMapper applicationExtMapper;
    private final BoardExtMapper boardExtMapper;
    private final UserExtMapper userExtMapper;
    private final BoardService boardService;

    public ApplicationServiceImpl(ApplicationExtMapper applicationExtMapper, BoardExtMapper boardExtMapper, UserExtMapper userExtMapper, BoardService boardService) {
        this.applicationExtMapper = applicationExtMapper;
        this.boardExtMapper = boardExtMapper;
        this.userExtMapper = userExtMapper;
        this.boardService = boardService;
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

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Object findApplication(String applicationFlag) {

        User user = RequestContext.getUserInfo();

        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER && !user.getApplicationFlag().equals(applicationFlag)) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        Application application = applicationExtMapper.findApplicationByFlag(applicationFlag);
        if(application == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        return application;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Object listApplication(Integer page, Integer size, String applicationName) {

        User user = RequestContext.getUserInfo();

        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        PageHelper.startPage(page,size);
        List<Application> applications = applicationExtMapper.listApplication(applicationName);
        PageInfo<Application> pageInfo = new PageInfo<>(applications);
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object saveApplication(Application application) {

        User user = RequestContext.getUserInfo();
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        if(StringUtils.isEmpty(application.getApplicationName())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(StringUtils.isEmpty(application.getApplicationCompany())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(StringUtils.isEmpty(application.getApplicationFlag())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        checkApplicationFlag(application.getApplicationFlag());

        applicationExtMapper.insert(application);

        return application;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateApplication(Long applicationId, Application application) {

        User user = RequestContext.getUserInfo();
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }
        if(applicationId == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(StringUtils.isEmpty(application.getApplicationName())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(StringUtils.isEmpty(application.getApplicationCompany())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        Application applicationInfo = applicationExtMapper.selectByPrimaryKey(applicationId);
        if(applicationInfo == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }


        applicationExtMapper.updateByPrimaryKeySelective(Application.builder()
                                                                    .applicationId(applicationId)
                                                                    .applicationName(application.getApplicationName())
                                                                    .applicationCompany(application.getApplicationCompany())
                                                                    .applicationDescription(application.getApplicationDescription()).build());
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Object listApplicationFlags() {

        User user = RequestContext.getUserInfo();
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        return applicationExtMapper.listApplicationFlags();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object removeApplication(Long applicationId) {

        User user = RequestContext.getUserInfo();
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }
        Application application = applicationExtMapper.selectByPrimaryKey(applicationId);
        if(application == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        List<Board> boards = boardExtMapper.listBoardByFlag(application.getApplicationFlag());
        boards.forEach(b -> boardService.removeBoard(b.getBoardId()));
        //userExtMapper.removeAllUserByApplicationFlag(application.getApplicationFlag());
        applicationExtMapper.deleteByPrimaryKey(applicationId);

        return null;
    }
}






















