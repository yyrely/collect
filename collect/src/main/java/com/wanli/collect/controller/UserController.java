package com.wanli.collect.controller;

import com.wanli.collect.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public Object findUserInfo(@RequestParam("userId") Long userId) {
        return userService.findUserInfo(userId);
    }
}
