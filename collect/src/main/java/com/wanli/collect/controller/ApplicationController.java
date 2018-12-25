package com.wanli.collect.controller;

import com.wanli.collect.service.ApplicationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hu
 * @date 2018/12/25 16:16
 */

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * 检验应用标识是否可用
     * @param applicationFlag
     * @return
     */
    @PostMapping("/check/{flag}")
    public Object checkApplicationFlag(@PathVariable("flag") String applicationFlag) {
        return applicationService.checkApplicationFlag(applicationFlag);
    }

}
