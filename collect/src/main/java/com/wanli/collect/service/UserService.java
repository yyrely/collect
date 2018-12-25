package com.wanli.collect.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wanli.collect.model.dto.UserDTO;
import com.wanli.collect.model.entity.User;
import com.wanli.collect.model.vo.UserVO;

/**
 * @author Hu
 * @date 2018/12/23 19:22
 */

public interface UserService {

    Object findUserInfo(Long userId);

    /**
     * 用户登录
     * @param userDTO
     */
    Object login(UserDTO userDTO) throws JsonProcessingException;

    /**
     * 创建用户
     * @param userDTO
     * @return
     */
    Object register(UserDTO userDTO);

    /**
     * 登出
     * @return
     */
    Object logout();

    /**
     * 用户列表
     * @return
     */
    Object listUsers();
}
