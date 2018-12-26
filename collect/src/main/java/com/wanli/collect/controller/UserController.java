package com.wanli.collect.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.annotations.Delete;
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

    @GetMapping("/{userId}")
    public Object findUserInfo(@PathVariable("userId") Long userId) {
        return userService.findUser(userId);
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

    /**
     * 修改自己密码
     * @param userId
     * @param userDTO
     * @return
     */
    @PutMapping("/{userId}/passwords")
    public Object updatePassword(@PathVariable("userId") Long userId,
                                 @RequestBody UserDTO userDTO) {
        return userService.updatePassword(userId, userDTO);
    }

    /**
     * 管理员修改子用户密码
     * @param userId
     * @param userDTO
     * @return
     */
    @PutMapping("/{userId}")
    public Object updateUser(@PathVariable("userId") Long userId,
                                 @RequestBody UserDTO userDTO) {
        return userService.updateUser(userId, userDTO);
    }

    @DeleteMapping("/{userId}")
    public Object removeUser(@PathVariable("userId") Long userId) {
        return userService.removeUser(userId);
    }


}












