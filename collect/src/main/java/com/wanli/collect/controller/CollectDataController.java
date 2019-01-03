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
@RequestMapping("/transducers/datas")
public class CollectDataController {

    private final CollectDataService collectDataService;

    public CollectDataController(CollectDataService collectDataService) {
        this.collectDataService = collectDataService;
    }

    /**
     * 获取传感器数据列表
     * @param transducerKeyBean
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public Object listData(TransducerKeyBean transducerKeyBean,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "size", defaultValue = "10") Integer size) {

        return collectDataService.listData(transducerKeyBean, page ,size);
    }
}
