package com.example.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.lang.Result;
import com.example.entity.Blog;
import com.example.service.IBlogService;
import com.example.util.ShiroUtils;
import io.jsonwebtoken.lang.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author alex tang
 * @since 2022-05-11
 */
@RestController
public class BlogController {

    @Autowired
    IBlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer curPage) {
        Page page = new Page(curPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.succ(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable("id")Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "blog is deleted");
        return Result.succ(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog temp;
        if(blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            Assert.isTrue(temp.getUserId().equals(ShiroUtils.getProfile().getId()), "unauthenticated");
        } else {
            temp = new Blog();
            temp.setCreated(LocalDateTime.now());
            temp.setUserId(ShiroUtils.getProfile().getId());
            temp.setStatus(0);
        }
        BeanUtils.copyProperties(blog, temp, "userId", "created", "id", "status");
        blogService.saveOrUpdate(temp);
        return Result.succ(null);
    }

}
