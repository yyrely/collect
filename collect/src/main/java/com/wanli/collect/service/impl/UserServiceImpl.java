package com.wanli.collect.service.impl;

import com.wanli.collect.dao.mapper.ext.UserExtMapper;
import com.wanli.collect.model.entity.User;
import com.wanli.collect.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Hu
 * @date 2018/12/23 19:23
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserExtMapper userExtMapper;

    public UserServiceImpl(UserExtMapper userExtMapper) {
        this.userExtMapper = userExtMapper;
    }

    @Override
    public User findUserInfo(Long userId) {
        return userExtMapper.selectByPrimaryKey(userId);
    }


}
