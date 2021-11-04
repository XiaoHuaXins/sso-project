package com.smart.sso.server.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.smart.sso.server.dao.userlogin.LoginDao;
import com.smart.sso.server.util.secure.MD5Secure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.sso.client.rpc.Result;
import com.smart.sso.client.rpc.SsoUser;
import com.smart.sso.server.model.User;
import com.smart.sso.server.service.UserService;
import sun.misc.BASE64Encoder;

@Service("userService")
public class UserServiceImpl implements UserService {
	

	@Autowired
	MD5Secure md5Secure;
	@Autowired
	LoginDao loginDao;
	private static final BASE64Encoder base64Encoder = new BASE64Encoder();
	@Override
	public Result<SsoUser> login(String username, String password) throws Exception {
		String p = loginDao.findPasswordByUserId(1);
		byte[] encrypt = md5Secure.encrypt(password.getBytes(StandardCharsets.UTF_8));
		String encode = base64Encoder.encode(encrypt);
		System.out.println(encode);
		if(p.equals(encode)){
			return Result.createSuccess(new SsoUser(1, password));
		}
		return Result.createError("密码有误");
	}
}
