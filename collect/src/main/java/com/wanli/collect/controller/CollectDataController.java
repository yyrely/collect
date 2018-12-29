package com.wanli.collect.controller;

import com.wanli.collect.model.domain.TransducerKeyBean;
import com.wanli.collect.service.CollectDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hu
 * @date 2018/12/29 13:51
 */

@RestController
@RequestMapping("/datas")
public class CollectDataController {

    private final CollectDataService collectDataService;

    public CollectDataController(CollectDataService collectDataService) {
        this.collectDataService = collectDataService;
    }

    @GetMapping
    public Object listData(TransducerKeyBean transducerKeyBean,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer size) {

        return collectDataService.listData(transducerKeyBean, page ,size);
    }
}
