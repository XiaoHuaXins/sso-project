package com.smart.sso.demo.controller;

import com.smart.sso.demo.entity.vo.Result;
import com.smart.sso.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xhx
 * @Date 2022/1/29 11:53
 * 账户管理
 */
@RestController("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/add")
    public Result addUser(@RequestParam("name") String name,
                          @RequestParam("phone") Long phone) {
        return Result.createSuccess();
    }
}
