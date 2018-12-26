package com.wanli.collect.controller;

import com.wanli.collect.service.ApplicationService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{flag}")
    public Object findApplication(@PathVariable("flag") String applicationFlag) {
        return applicationService.findApplication(applicationFlag);
    }

    @GetMapping
    public Object listApplication(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                                  @RequestParam(value = "applicationName", defaultValue = "") String applicationName) {
        return applicationService.listApplication(page, size, applicationName);
    }


}






















