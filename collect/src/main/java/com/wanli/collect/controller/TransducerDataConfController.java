package com.wanli.collect.controller;

import com.wanli.collect.model.domain.TransducerKeyBean;
import com.wanli.collect.model.entity.TransducerDataConf;
import com.wanli.collect.service.TransducerDataConfService;
import org.springframework.web.bind.annotation.*;

/**
 * @author Hu
 * @date 2018/12/29 10:48
 */

@RestController
@RequestMapping("/transducers/conf")
public class TransducerDataConfController {

    private final TransducerDataConfService transducerDataConfService;

    public TransducerDataConfController(TransducerDataConfService transducerDataConfService) {
        this.transducerDataConfService = transducerDataConfService;
    }

    /**
     * 获取传感器配置信息
     * @param transducerKeyBean
     * @return
     */
    @GetMapping
    public Object findTransducerDataConf(TransducerKeyBean transducerKeyBean) {
        return transducerDataConfService.findTransducerDataConf(transducerKeyBean);
    }

    @PutMapping("/{confId}")
    public Object updateTransducerDataConf(
            @PathVariable("confId") Long id,
            @RequestBody TransducerDataConf transducerDataConf){
        return transducerDataConfService.updateTransducerDataConf(id, transducerDataConf);
    }

}

