package com.wanli.collect.service;

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
}
