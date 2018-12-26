package com.wanli.collect.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wanli.collect.context.RequestContext;
import com.wanli.collect.dao.mapper.ext.BoardExtMapper;
import com.wanli.collect.exception.BaseErrorCode;
import com.wanli.collect.exception.ServiceException;
import com.wanli.collect.model.constants.UserStatusType;
import com.wanli.collect.model.entity.Board;
import com.wanli.collect.model.entity.User;
import com.wanli.collect.service.BoardService;

/**
 * @author Hu
 * @date 2018/12/26 16:57
 */

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardExtMapper boardExtMapper;

    public BoardServiceImpl(BoardExtMapper boardExtMapper) {
        this.boardExtMapper = boardExtMapper;
    }

    @Override
    public Object listBoardByFlag(String applicationFlag) {

        User user = RequestContext.getUserInfo();

        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER && !user.getApplicationFlag().equals(applicationFlag)) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        return boardExtMapper.listBoardByFlag(applicationFlag);
    }

    @Override
    public Object findBoardInfo(String boardId) {

        User user = RequestContext.getUserInfo();

        Board board = boardExtMapper.selectByPrimaryKey(boardId);
        if(board == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(user.getUserStatus() != null && !board.getApplicationFlag().equals(user.getApplicationFlag())) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        return board;
    }
}
