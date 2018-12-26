package com.wanli.collect.service;

/**
 * @author Hu
 * @date 2018/12/26 16:57
 */

public interface BoardService {

    /**
     * 获取板列表
     * @param applicationFlag
     * @return
     */
    Object listBoardByFlag(String applicationFlag);

    /**
     * 单块板信息
     * @param boardId
     * @return
     */
    Object findBoardInfo(String boardId);
}
