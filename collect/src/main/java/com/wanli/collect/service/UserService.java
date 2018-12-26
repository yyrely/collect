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

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    Object findUser(Long userId);

    /**
     * 用户登录
     * @param userDTO
     */
    Object login(UserDTO userDTO) throws Exception;

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

    /**
     * 修改密码
     * @param userId
     * @param userDTO
     * @return
     */
    Object updatePassword(Long userId, UserDTO userDTO);

    /**
     * 更新用户信息
     * @param userId
     * @param userDTO
     * @return
     */
    Object updateUser(Long userId, UserDTO userDTO);

    /**
     * 删除子用户
     * @param userId
     * @return
     */
    Object removeUser(Long userId);
}
