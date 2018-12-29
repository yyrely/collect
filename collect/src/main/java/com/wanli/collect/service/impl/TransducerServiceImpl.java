package com.wanli.collect.service.impl;

import com.wanli.collect.context.RequestContext;
import com.wanli.collect.dao.mapper.ext.BoardExtMapper;
import com.wanli.collect.dao.mapper.ext.TransducerExtMapper;
import com.wanli.collect.exception.BaseErrorCode;
import com.wanli.collect.exception.ServiceException;
import com.wanli.collect.model.constants.UserStatusType;
import com.wanli.collect.model.entity.Board;
import com.wanli.collect.model.entity.Transducer;
import com.wanli.collect.model.entity.User;
import com.wanli.collect.service.TransducerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hu
 * @date 2018/12/27 14:34
 */

@Service
public class TransducerServiceImpl implements TransducerService {

    private final TransducerExtMapper transducerExtMapper;
    private final BoardExtMapper boardExtMapper;

    public TransducerServiceImpl(TransducerExtMapper transducerExtMapper, BoardExtMapper boardExtMapper) {
        this.transducerExtMapper = transducerExtMapper;
        this.boardExtMapper = boardExtMapper;
    }

    @Override
    public Object listTransducerByBoardId(String boardId) {

        User user = RequestContext.getUserInfo();
        Board board = boardExtMapper.selectByPrimaryKey(boardId);
        if(board == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER && !user.getApplicationFlag().equals(board.getApplicationFlag())) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        List<Transducer> list = transducerExtMapper.listTransducerByBoardId(boardId);

        return list;
    }

    @Override
    public Object findTransducerById(Long id) {

        User user = RequestContext.getUserInfo();

        Transducer transducer = transducerExtMapper.selectByPrimaryKey(id);
        if(transducer == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        Board board = boardExtMapper.selectByPrimaryKey(transducer.getBoardId());
        if(!user.getApplicationFlag().equals(board.getApplicationFlag())) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        return transducer;
    }
}




























