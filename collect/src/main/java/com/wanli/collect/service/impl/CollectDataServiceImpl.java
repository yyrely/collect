package com.wanli.collect.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wanli.collect.context.RequestContext;
import com.wanli.collect.dao.mapper.ext.BoardExtMapper;
import com.wanli.collect.dao.mapper.ext.CollectDataExtMapper;
import com.wanli.collect.exception.BaseErrorCode;
import com.wanli.collect.exception.ServiceException;
import com.wanli.collect.model.constants.UserStatusType;
import com.wanli.collect.model.domain.TransducerKeyBean;
import com.wanli.collect.model.entity.Board;
import com.wanli.collect.model.entity.CollectData;
import com.wanli.collect.model.entity.User;
import com.wanli.collect.service.CollectDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Hu
 * @date 2018/12/29 13:53
 */

@Service
public class CollectDataServiceImpl implements CollectDataService {

    private final CollectDataExtMapper collectDataExtMapper;
    private final BoardExtMapper boardExtMapper;

    public CollectDataServiceImpl(CollectDataExtMapper collectDataExtMapper, BoardExtMapper boardExtMapper) {
        this.collectDataExtMapper = collectDataExtMapper;
        this.boardExtMapper = boardExtMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Object listData(TransducerKeyBean transducerKeyBean, Integer page, Integer size) {

        User user = RequestContext.getUserInfo();
        if(transducerKeyBean == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(transducerKeyBean.getBoardId() == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(transducerKeyBean.getTransducerType() == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(transducerKeyBean.getTransducerId() == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        Board board = boardExtMapper.selectByPrimaryKey(transducerKeyBean.getBoardId());
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER && !user.getApplicationFlag().equals(board.getApplicationFlag())) {
           throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        PageHelper.startPage(page,size);
        List<CollectData> collectDataList = collectDataExtMapper.listData(transducerKeyBean);

        PageInfo<CollectData> pageInfo = new PageInfo<>(collectDataList);

        return pageInfo;
    }
}


















