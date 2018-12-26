package com.wanli.collect.controller;

import com.wanli.collect.service.BoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("/{boardId}")
    public Object findBoardInfo(@PathVariable("boardId") String boardId) {
        return boardService.findBoardInfo(boardId);
    }




}
