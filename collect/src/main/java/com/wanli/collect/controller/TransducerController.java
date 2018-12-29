package com.wanli.collect.controller;

import com.wanli.collect.service.TransducerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hu
 * @date 2018/12/27 14:32
 */

@RestController
@RequestMapping("/transducers")
public class TransducerController {

    private final TransducerService transducerService;

    public TransducerController(TransducerService transducerService) {
        this.transducerService = transducerService;
    }

    /**
     * 获取传感器列表
     * @param boardId
     * @return
     */
    @GetMapping
    public Object listTransducer(String boardId) {
        return transducerService.listTransducerByBoardId(boardId);
    }

    /**
     * 获取传感器详情
     * @param id
     * @return
     */
    @GetMapping("/{transducerId}")
    public Object findTransducer(@PathVariable("transducerId") Long id) {
        return transducerService.findTransducerById(id);
    }


}
