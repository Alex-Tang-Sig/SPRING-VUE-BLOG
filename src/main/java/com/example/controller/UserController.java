package com.example.controller;


import com.example.common.lang.Result;
import com.example.entity.User;
import com.example.service.IUserService;
import com.example.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author alex tang
 * @since 2022-05-11
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/index")
    public Result index() {
        User user = userService.getById(1L);
        return Result.succ(200, "success", user);
    }

}
