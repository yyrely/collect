package com.wanli.collect.service;

import com.wanli.collect.model.entity.Board;

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

    /**
     * 验证板id是否可用
     * @param boardId
     * @return
     */
    Object checkBoardId(String boardId);

    /**
     * 保存板信息
     * @param board
     * @return
     */
    Object saveBoard(Board board);

    /**
     * 更新板
     * @param boardId
     * @param board
     * @return
     */
    Object updateBoard(String boardId, Board board);

    /**
     * 删除板
     * @param boardId
     * @return
     */
    Object removeBoard(String boardId);
}
