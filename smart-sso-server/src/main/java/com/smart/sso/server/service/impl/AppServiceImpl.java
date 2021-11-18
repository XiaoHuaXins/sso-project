package com.smart.sso.server.service.impl;

import com.smart.sso.server.dao.appinfo.AppInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smart.sso.client.rpc.Result;
import com.smart.sso.server.service.AppService;

@Service("appService")
public class AppServiceImpl implements AppService {

	@Autowired
	AppInfoDao appInfoDao;

	@Override
	public boolean exists(String appId) {
		String appSecretByAppId = appInfoDao.findAppSecretByAppId(appId);
		if(appSecretByAppId == null) {
			return false;
		}
		return true;
	}

	@Override
	public Result<Void> validate(String appId, String appSecret) {
		String appSecretByAppId = appInfoDao.findAppSecretByAppId(appId);
		if(appSecretByAppId == null) {
			return Result.createError("appId不存在");
		}
		if (appSecretByAppId.equals(appSecret)) {
			return Result.success();
		}
		return Result.createError("密钥错误！");
	}
}
