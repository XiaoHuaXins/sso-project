package com.smart.sso.demo.service.impl;

import com.smart.sso.demo.dao.userinfo.UserInfoDao;
import com.smart.sso.demo.entity.user.UserInfo;
import com.smart.sso.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author xhx
 * @Date 2022/1/24 18:45
 */
@Service
public class UserInfoServiceImpl implements UserService {
    @Autowired
    UserInfoDao userInfoDao;
    @Override
    public void addUserInfo(UserInfo userInfo) {
        userInfoDao.addUserInfo(userInfo);
    }

    @Override
    public UserInfo findUserInfoById(int id) {
        return userInfoDao.findUserInfoById(id);

    }
}
