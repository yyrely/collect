package com.wanli.collect.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.wanli.collect.context.RequestContext;
import com.wanli.collect.dao.mapper.ext.BoardExtMapper;
import com.wanli.collect.dao.mapper.ext.TransducerDataConfExtMapper;
import com.wanli.collect.dao.mapper.ext.TransducerExtMapper;
import com.wanli.collect.exception.BaseErrorCode;
import com.wanli.collect.exception.ServiceException;
import com.wanli.collect.model.constants.DataStatusType;
import com.wanli.collect.model.constants.UserStatusType;
import com.wanli.collect.model.domain.TransducerKeyBean;
import com.wanli.collect.model.dto.TransducerDTO;
import com.wanli.collect.model.entity.Board;
import com.wanli.collect.model.entity.Transducer;
import com.wanli.collect.model.entity.TransducerDataConf;
import com.wanli.collect.model.entity.User;
import com.wanli.collect.model.vo.TransducerVO;
import com.wanli.collect.service.TransducerService;

/**
 * @author Hu
 * @date 2018/12/27 14:34
 */

@Service
public class TransducerServiceImpl implements TransducerService {

    private final TransducerExtMapper transducerExtMapper;
    private final BoardExtMapper boardExtMapper;
    private final TransducerDataConfExtMapper transducerDataConfExtMapper;



    public TransducerServiceImpl(TransducerExtMapper transducerExtMapper, BoardExtMapper boardExtMapper, TransducerDataConfExtMapper transducerDataConfExtMapper) {
        this.transducerExtMapper = transducerExtMapper;
        this.boardExtMapper = boardExtMapper;
        this.transducerDataConfExtMapper = transducerDataConfExtMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
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
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Object findTransducerById(Long id) {

        User user = RequestContext.getUserInfo();

        Transducer transducer = transducerExtMapper.selectByPrimaryKey(id);
        if(transducer == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        Board board = boardExtMapper.selectByPrimaryKey(transducer.getBoardId());
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER && !user.getApplicationFlag().equals(board.getApplicationFlag())) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        return transducer;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object saveTransducer(TransducerDTO transducerDTO) {

        User user = RequestContext.getUserInfo();
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        if(StringUtils.isEmpty(transducerDTO.getTransducerDataConf().getApplicationFlag())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(StringUtils.isEmpty(transducerDTO.getBoardId())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(StringUtils.isEmpty(transducerDTO.getTransducerType())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(StringUtils.isEmpty(transducerDTO.getTransducerId())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }
        if(StringUtils.isEmpty(transducerDTO.getTransducerUnit())) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        Transducer transducer = transducerExtMapper.findTransducer(transducerDTO.getBoardId(), transducerDTO.getTransducerType(), transducerDTO.getTransducerId());
        if(transducer != null) {
            throw new ServiceException(BaseErrorCode.TRANSDUCER_EXIST);
        }

        transducer = new Transducer();
        BeanUtils.copyProperties(transducerDTO, transducer);
        transducer.setTransducerNowdata(BigDecimal.ZERO);
        transducer.setTransducerStatus(DataStatusType.NORMAL);
        transducerExtMapper.insert(transducer);

        TransducerDataConf transducerDataConf = transducerDTO.getTransducerDataConf();
        transducerDataConf.setBoardId(transducerDTO.getBoardId());
        transducerDataConf.setTransducerType(transducerDTO.getTransducerType());
        transducerDataConf.setTransducerId(transducerDTO.getTransducerId());
        transducerDataConfExtMapper.insert(transducerDataConf);

        TransducerVO transducerVO = new TransducerVO();
        BeanUtils.copyProperties(transducer, transducerVO);
        transducerVO.setTransducerDataConf(transducerDataConf);
        return transducerVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateTransducer(Long id, TransducerDTO transducerDTO) {

        User user = RequestContext.getUserInfo();
        if(user.getUserStatus() == UserStatusType.NORMAL) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }
        if(id == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        Transducer transducer = transducerExtMapper.selectByPrimaryKey(id);

        if(transducer == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        Board board = boardExtMapper.selectByPrimaryKey(transducer.getBoardId());

        if(user.getUserStatus() == UserStatusType.CHARGE && !user.getApplicationFlag().equals(board.getApplicationFlag())) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        transducer.setTransducerDescription(transducerDTO.getTransducerDescription());
        transducerExtMapper.updateByPrimaryKeySelective(transducer);
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object removeTransducer(Long id) {

        User user = RequestContext.getUserInfo();
        if(user.getUserStatus() != UserStatusType.GENERAL_MANAGER) {
            throw new ServiceException(BaseErrorCode.AUTHORITY_ILLEGAL);
        }

        Transducer transducer = transducerExtMapper.selectByPrimaryKey(id);
        if(transducer == null) {
            throw new ServiceException(BaseErrorCode.PARAM_ILLEGAL);
        }

        TransducerKeyBean transducerKeyBean = new TransducerKeyBean();
        transducerKeyBean.setBoardId(transducer.getBoardId());
        transducerKeyBean.setTransducerType(transducer.getTransducerType());
        transducerKeyBean.setTransducerId(transducer.getTransducerId());

        transducerDataConfExtMapper.removeTransducerDataConf(transducerKeyBean);
        transducerExtMapper.deleteByPrimaryKey(id);

        return null;
    }
}




























