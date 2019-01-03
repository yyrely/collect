package com.wanli.collect.service;

import com.wanli.collect.model.dto.TransducerDTO;

/**
 * @author Hu
 * @date 2018/12/27 14:34
 */

public interface TransducerService {

    /**
     * 根据板id获取传感器列表
     * @param boardId
     * @return
     */
    Object listTransducerByBoardId(String boardId);

    /**
     * 根据主键id获取传感器信息
     * @param id
     * @return
     */
    Object findTransducerById(Long id);

    /**
     * 保存传感器
     * @param transducerDTO
     * @return
     */
    Object saveTransducer(TransducerDTO transducerDTO);


    /**
     * 更新传感器
     * @param id
     * @param transducerDTO
     * @return
     */
    Object updateTransducer(Long id, TransducerDTO transducerDTO);

    /**
     * 删除传感器
     * @param id
     * @return
     */
    Object removeTransducer(Long id);
}
