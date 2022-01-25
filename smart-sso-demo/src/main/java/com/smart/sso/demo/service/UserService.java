package com.smart.sso.demo.service;

import com.smart.sso.demo.entity.user.UserInfo;

import java.util.List;

/**
 * @Author xhx
 * @Date 2022/1/24 18:30
 */
public interface UserService {
    void addUserInfo(UserInfo userInfo);

    UserInfo findUserInfoById(int id);
}
