package com.wanli.collect.controller;

import com.wanli.collect.model.dto.TransducerDTO;
import com.wanli.collect.service.TransducerService;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加传感器
     * @param transducerDTO
     * @return
     */
    @PostMapping
    public Object saveTransducer(@RequestBody TransducerDTO transducerDTO) {
        return transducerService.saveTransducer(transducerDTO);
    }

    /**
     * 更新传感器
     * @param
     * @return
     */
    @PutMapping("/{transducerId}")
    public Object saveTransducer(@PathVariable("transducerId") Long id,
                                @RequestBody TransducerDTO transducerDTO) {
        return transducerService.updateTransducer(id,transducerDTO);
    }

    /**
     * 删除传感器
     * @param id
     * @return
     */
    @DeleteMapping("/{transducerId}")
    public Object removeTransducer(@PathVariable("transducerId") Long id) {
        return transducerService.removeTransducer(id);
    }


}
