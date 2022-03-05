package com.smart.sso.demo.controller;

import com.smart.sso.client.constant.SsoConstant;
import com.smart.sso.client.rpc.SsoUser;
import com.smart.sso.client.session.SessionAccessToken;
import com.smart.sso.demo.entity.vo.Result;
import com.smart.sso.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author xhx
 * @Date 2022/1/29 11:53
 * 账户管理
 */
@Controller("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/add")
    @ResponseBody
    public Result addUser(@RequestParam("name") String name,
                          @RequestParam("phone") Long phone) {
        return Result.createSuccess();
    }

    /**
     * 默认注册方法，使用CAS服务器资源的信息进行注册
     * @param redirect
     * @param session
     * @return
     */
    //TODO 是否需要在本地坐权限缓存信息，避免每次请求接口的时候都去查询redis
    @RequestMapping("/defaultRegister")
    public String SsoUserRegister(@RequestParam("redirectUrl")String redirect, HttpSession session) {
        SessionAccessToken attribute = (SessionAccessToken) session.getAttribute(SsoConstant.SESSION_ACCESS_TOKEN);
        SsoUser user = attribute.getUser();
        userService.addUserOfSso(user);
        return "redirect:" + redirect;
    }
}
