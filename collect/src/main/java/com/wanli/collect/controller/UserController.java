package com.wanli.collect.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import com.wanli.collect.model.dto.UserDTO;
import com.wanli.collect.service.UserService;

/**
 * @author Hu
 * @date 2018/12/23 15:23
 */

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public Object findUserInfo(@RequestParam("userId") Long userId) {
        return userService.findUserInfo(userId);
    }

    /**
     * 登录接口
     * @param userDTO
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public Object login(@RequestBody UserDTO userDTO) throws Exception {
        return userService.login(userDTO);
    }

    /**
     * 创建用户
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    public Object register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    /**
     * 登出
     * @return
     */
    @PostMapping("/logout")
    public Object logout() {
        return userService.logout();
    }

    /**
     * 获取用户列表
     * @return
     */
    @GetMapping
    public Object listUsers() {
        return userService.listUsers();
    }


}












