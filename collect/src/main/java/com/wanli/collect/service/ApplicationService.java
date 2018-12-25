package com.wanli.collect.service;

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
}
