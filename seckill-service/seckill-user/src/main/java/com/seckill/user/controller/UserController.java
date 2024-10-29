package com.seckill.user.controller;

import com.github.pagehelper.PageInfo;
import com.seckill.user.pojo.User;
import com.seckill.user.service.UserService;
import com.seckill.util.JwtTokenUtil;
import com.seckill.util.Result;
import com.seckill.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author http://www.itheima.com
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * user分页条件搜索实现
     */
    @RequestMapping(value = "/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) User user, @PathVariable int page, @PathVariable int size) throws InterruptedException {
        //调用ParaService实现分页条件查询Para
        PageInfo<User> pageInfo = userService.findPage(user, page, size);
        return new Result<>(true, StatusCode.OK, "查询成功", pageInfo);
    }


    public static void main(String[] args) {
        System.out.println(DigestUtils.md5DigestAsHex("itcast".getBytes()));
    }

}
