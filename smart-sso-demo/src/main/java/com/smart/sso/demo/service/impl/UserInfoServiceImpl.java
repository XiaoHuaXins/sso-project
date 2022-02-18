package com.smart.sso.demo.service.impl;

import com.smart.sso.client.rpc.SsoUser;
import com.smart.sso.demo.constant.RedisConstant;
import com.smart.sso.demo.dao.userinfo.UserInfoDao;
import com.smart.sso.demo.entity.user.UserInfo;
import com.smart.sso.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author xhx
 * @Date 2022/1/24 18:45
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserService {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public void addUserInfo(UserInfo userInfo) {
        userInfoDao.addUserInfo(userInfo);
    }

    @Override
    public UserInfo findUserInfoById(int id) {
        return userInfoDao.findUserInfoById(id);

    }

    @Override
    public void addUserOfSso(SsoUser user) {
        UserInfo userInfo = new UserInfo();
        redisTemplate.opsForHash().putIfAbsent(RedisConstant.USER_KEY + ":" + RedisConstant.USER_AUTH,userInfo.getUserId(), userInfo.getRoleId());
        log.info("增加用户业务逻辑！");
    }
}
