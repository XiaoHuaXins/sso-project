package com.smart.sso.server.service.login.impl;

import com.smart.sso.server.dao.userlogin.LoginDao;
import com.smart.sso.server.service.login.LoginService;
import com.smart.sso.server.util.secure.MD5Secure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @Author xhx
 * @Date 2021/11/1 21:50
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Value("{sso.server.password.secret}")
    String secret;
    @Autowired
    LoginDao loginDao;
    @Autowired
    MD5Secure md5Secure;

    public final String COMMOM_USER = "common";
    @Override
    public int addCommonUser(String userName, String password, String email) throws Exception {
        password = new String(md5Secure.encrypt((password + secret).getBytes(StandardCharsets.UTF_8)));
        int userId = Integer.parseInt(userName);
        return loginDao.insertUser(userId, password, email);
    }
}
