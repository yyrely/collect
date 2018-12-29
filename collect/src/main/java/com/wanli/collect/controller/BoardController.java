package com.wanli.collect.controller;

import com.wanli.collect.model.entity.Board;
import com.wanli.collect.service.BoardService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Hu
 * @date 2018/12/26 16:47
 */

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /**
     * 获取板列表
     * @param applicationFlag
     * @return
     */
    @GetMapping("/applications/{flag}")
    public Object listBoardByFlag(@PathVariable("flag") String applicationFlag) {
        return boardService.listBoardByFlag(applicationFlag);
    }

    /**
     * 单块板信息
     * @param boardId
     * @return
     */
    @GetMapping("/{boardId}")
    public Object findBoardInfo(@PathVariable("boardId") String boardId) {
        return boardService.findBoardInfo(boardId);
    }

    /**
     * 验证板id
     * @param boardId
     * @return
     */
    @PostMapping("/check/{boardId}")
    public Object checkBoardId(@PathVariable("boardId") String boardId) {
        return boardService.checkBoardId(boardId);
    }

    /**
     * 添加板
     * @param board
     * @return
     */
    @PostMapping
    public Object saveBoard(@RequestBody Board board) {
        return boardService.saveBoard(board);
    }

    /**
     * 更新板(名字)
     * @param boardId
     * @param board
     * @return
     */
    @PutMapping("/{boardId}")
    public Object updateBoard(@PathVariable("boardId") String boardId, @RequestBody Board board) {
        return boardService.updateBoard(boardId, board);
    }


    /**
     * 删除板
     * @param boardId
     * @return
     */
    @DeleteMapping("/{boardId}")
    public Object removeBoard(@PathVariable("boardId") String boardId) {
        return boardService.removeBoard(boardId);
    }





}
