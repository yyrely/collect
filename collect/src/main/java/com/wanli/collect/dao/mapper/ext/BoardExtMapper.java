package com.wanli.collect.dao.mapper.ext;

import com.wanli.collect.model.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardExtMapper extends com.wanli.collect.dao.mapper.BoardMapper {

    /**
     * 根据应用标识获取板列表
     * @param applicationFlag
     * @return
     */
    List<Board> listBoardByFlag(String applicationFlag);
}