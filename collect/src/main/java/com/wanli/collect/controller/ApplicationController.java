package com.wanli.collect.controller;

import com.wanli.collect.model.entity.Application;
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

    /**
     * 获取应用信息
     * @param applicationFlag
     * @return
     */
    @GetMapping("/{flag}")
    public Object findApplication(@PathVariable("flag") String applicationFlag) {
        return applicationService.findApplication(applicationFlag);
    }

    /**
     * 管理员获取应用列表
     * @param page
     * @param size
     * @param applicationName
     * @return
     */
    @GetMapping
    public Object listApplication(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                                  @RequestParam(value = "applicationName", defaultValue = "") String applicationName) {
        return applicationService.listApplication(page, size, applicationName);
    }

    /**
     * 添加应用
     * @param application
     * @return
     */
    @PostMapping
    public Object saveApplication(@RequestBody Application application) {
        return applicationService.saveApplication(application);
    }

    /**
     * 更新应用
     * @param applicationId
     * @param application
     * @return
     */
    @PutMapping("/{applicationId}")
    public Object updateApplication(@PathVariable("applicationId") Long applicationId,
                                    @RequestBody Application application) {
        return applicationService.updateApplication(applicationId, application);
    }

    /**
     * 删除应用
     * @param applicationId
     * @return
     */
    @DeleteMapping("/{applicationId}")
    public Object removeApplication(@PathVariable("applicationId") Long applicationId) {
        //TODO 删除应用待完成
        return null;
    }






}






















