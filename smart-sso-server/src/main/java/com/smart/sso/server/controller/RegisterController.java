package com.smart.sso.server.controller;

import com.smart.sso.server.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xhx
 * @Date 2021/11/1 21:54
 */
@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    LoginService loginService;

    @RequestMapping("/add")
    public String addUser(@RequestParam("userName") String userName, @RequestParam("password")String password, @RequestParam("email")String email) throws Exception {
        loginService.addCommonUser(userName, password, email);
        return "成功";
    }


}
