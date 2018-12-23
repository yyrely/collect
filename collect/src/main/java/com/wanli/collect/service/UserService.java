package com.wanli.collect.service;

import com.wanli.collect.model.entity.User;

/**
 * @author Hu
 * @date 2018/12/23 19:22
 */

public interface UserService {

    User findUserInfo(Long userId);
}
