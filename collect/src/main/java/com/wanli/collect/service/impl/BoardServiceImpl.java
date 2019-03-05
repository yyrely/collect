package com.wanli.collect.service.impl;

import java.time.Instant;

import com.wanli.collect.dao.mapper.ext.TransducerDataConfExtMapper;
import com.wanli.collect.dao.mapper.ext.TransducerExtMapper;
import com.wanli.collect.model.constants.DataStatusType;
import org.springframework.stereotype.Service;

import com.wanli.collect.context.RequestContext;
import com.wanli.collect.dao.mapper.ext.BoardExtMapper;
import com.wanli.collect.exception.BaseErrorCode;
import com.wanli.collect.exception.ServiceException;
import com.wanli.collect.model.constants.UserStatusType;
import com.wanli.collect.model.entity.Board;
import com.wanli.collect.model.entity.User;
import com.wanli.collect.service.BoardService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author Hu
 * @date 2018/12/26 16:57
 */

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardExtMapper boardExtMapper;
    private final TransducerExtMapper transducerExtMapper;
    private final TransducerDataConfExtMapper transducerDataConfExtMapper;

    public BoardServiceImpl(BoardExtMapper boardExtMapper, TransducerExtMapper transducerExtMapper, TransducerDataConfExtMapper transducerDataConfExtMapper) {
        this.boardExtMapper = boardExtMapper;
        this.transducerExtMapper = transducerExtMapper;
        this.transducerDataConfExtMapper = transducerDataConfExtMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Object listBoardByFlag(String applicationFlag) {

        User user = RequestContext.getUserInfo();

        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER && !user.getApplicationFlag().equals(applicationFlag)) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        return boardExtMapper.listBoardByFlag(applicationFlag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Object findBoardInfo(String boardId) {

        User user = RequestContext.getUserInfo();

        Board board = boardExtMapper.selectByPrimaryKey(boardId);
        if(board == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER && !board.getApplicationFlag().equals(user.getApplicationFlag())) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        return board;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Object checkBoardId(String boardId) {

        User user = RequestContext.getUserInfo();
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        if(StringUtils.isEmpty(boardId)) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        Board board = boardExtMapper.selectByPrimaryKey(boardId);
        if(board != null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object saveBoard(Board board) {

        User user = RequestContext.getUserInfo();
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        checkBoardId(board.getBoardId());
        if(StringUtils.isEmpty(board.getApplicationFlag())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(StringUtils.isEmpty(board.getBoardName())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        board.setBoardStatus(DataStatusType.NORMAL);
        Instant now = Instant.now();
        Instant createTime = Instant.ofEpochSecond(now.getEpochSecond());
        board.setBoardTime(createTime);
        boardExtMapper.insert(board);

        return board;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateBoard(String boardId, Board board) {

        User user = RequestContext.getUserInfo();
        if(user.getUserStatus() == UserStatusType.NORMAL) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        if(StringUtils.isEmpty(board.getBoardName())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(StringUtils.isEmpty(boardId)) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        Board boardInfo = boardExtMapper.selectByPrimaryKey(boardId);
        if(boardInfo == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(user.getUserStatus() == UserStatusType.CHARGE && !user.getApplicationFlag().equals(boardInfo.getApplicationFlag())) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        boardExtMapper.updateByPrimaryKeySelective(Board.builder()
                                                        .boardId(boardId)
                                                        .boardName(board.getBoardName())
                                                        .boardDescription(board.getBoardDescription())
                                                        .build());
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object removeBoard(String boardId) {

        User user = RequestContext.getUserInfo();
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        Board board = boardExtMapper.selectByPrimaryKey(boardId);
        if(board == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        transducerDataConfExtMapper.removeTransducerDataConfByBoardId(boardId);
        transducerExtMapper.removeTransducerByBoardId(boardId);
        boardExtMapper.deleteByPrimaryKey(boardId);

        return null;
    }
}


















