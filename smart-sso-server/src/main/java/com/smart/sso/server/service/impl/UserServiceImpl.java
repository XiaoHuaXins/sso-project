package com.smart.sso.server.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.smart.sso.server.dao.userlogin.LoginDao;
import com.smart.sso.server.model.dto.UserLoginDto;
import com.smart.sso.server.util.secure.MD5Secure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.smart.sso.client.rpc.Result;
import com.smart.sso.client.rpc.SsoUser;
import com.smart.sso.server.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Value("{sso.server.password.secret}")
	String secret;
	@Autowired
	MD5Secure md5Secure;
	@Autowired
	LoginDao loginDao;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Result<SsoUser> login(String username, String password) throws Exception {
		int userId = Integer.parseInt(username);
		UserLoginDto user = loginDao.findUser(userId);
		if(user == null) {
			return Result.createError("账号不存在！");
		}
		String encrypt2Base64 = md5Secure.getEncrypt2Base64((password + secret).getBytes(StandardCharsets.UTF_8));
		//logger.info("用户密钥：{}" , encrypt2Base64);
		if(user.getEnabled()) {
			return Result.createError("该账户已被封禁！");
		}
		if(user.getPassword().equals(encrypt2Base64)){
			return Result.createSuccess(new SsoUser(userId, user.getUserName()));
		}
		return Result.createError("密码有误");
	}

	@Override
	public void logout(Integer userName) {
		loginDao.updateLogoutStatus(userName);
	}
}
