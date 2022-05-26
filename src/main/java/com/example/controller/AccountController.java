package com.example.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.dto.LoginDto;
import com.example.common.lang.Result;
import com.example.entity.User;
import com.example.service.IUserService;
import com.example.util.JwtUtils;
import io.jsonwebtoken.lang.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.http.HttpResponse;

/**
 * AccountController class
 *
 * @author kejiwang
 * @date 23/5/22
 */
@RestController
public class AccountController {

    @Autowired
    IUserService userService;

    @Autowired
    JwtUtils jwtUtils;

    private final String AUTHORIZATION = "Authorization";


    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "User doesn't exist");
        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.fail("wrong password");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader(AUTHORIZATION, jwt);
        response.setHeader("Access-Control-Expose-Headers", AUTHORIZATION);
        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }
}
