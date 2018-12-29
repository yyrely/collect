package com.wanli.collect.service;

import com.wanli.collect.model.entity.Application;

/**
 * @author Hu
 * @date 2018/12/25 16:18
 */

public interface ApplicationService {

    /**
     * 检测应用标识是否存在
     * @param applicationFlag
     * @return
     */
    Object checkApplicationFlag(String applicationFlag);

    /**
     * 获取应用信息
     * @param applicationFlag
     * @return
     */
    Object findApplication(String applicationFlag);

    /**
     * 获取应用列表
     * @param page
     * @param size
     * @param applicationName
     * @return
     */
    Object listApplication(Integer page, Integer size, String applicationName);

    /**
     * 添加应用
     * @param application
     * @return
     */
    Object saveApplication(Application application);

    /**
     * 更新应用
     * @param applicationId
     * @param application
     * @return
     */
    Object updateApplication(Long applicationId, Application application);

    /**
     * 获取应用标识列表
     * @return
     */
    Object listApplicationFlags();

    /**
     * 删除应用
     * @param applicationId
     * @return
     */
    Object removeApplication(Long applicationId);
}
