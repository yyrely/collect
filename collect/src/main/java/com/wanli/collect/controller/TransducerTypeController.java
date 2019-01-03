package com.wanli.collect.controller;

import com.wanli.collect.model.entity.TransducerType;
import com.wanli.collect.service.TransducerTypeService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Hu
 * @date 2019/1/3 14:19
 */

@RestController
@RequestMapping("/transducers/types")
public class TransducerTypeController {

    private final TransducerTypeService transducerTypeService;

    public TransducerTypeController(TransducerTypeService transducerTypeService) {
        this.transducerTypeService = transducerTypeService;
    }

    /**
     * 获取传感器类型列表
     * @return
     */
    @GetMapping
    public Object listTransducerType() {
        return transducerTypeService.listTransducerType();
    }

    /**
     * 添加传感器类型
     * @param transducerType
     * @return
     */
    @PostMapping
    public Object saveTransducerType(@RequestBody TransducerType transducerType) {
        return transducerTypeService.saveTransducerType(transducerType);
    }

    /**
     * 更新传感器类型
     * @param id
     * @param transducerType
     * @return
     */
    @PutMapping("/{id}")
    public Object updateTransducerType(@PathVariable("id") Long id,
                                       @RequestBody TransducerType transducerType) {
        return transducerTypeService.updateTransducerType(id, transducerType);
    }

    @DeleteMapping("/{id}")
    public Object removeTransducerType(@PathVariable("id") Long id) {
        return transducerTypeService.removeTransducerType(id);
    }


}
