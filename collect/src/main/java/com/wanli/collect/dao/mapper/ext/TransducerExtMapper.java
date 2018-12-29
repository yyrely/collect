package com.wanli.collect.dao.mapper.ext;

import com.wanli.collect.model.entity.Transducer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TransducerExtMapper extends com.wanli.collect.dao.mapper.TransducerMapper {

    /**
     * 根据板id获取板中所有的传感器
     * @param boardId
     * @return
     */
    List<Transducer> listTransducerByBoardId(@Param("boardId") String boardId);
}