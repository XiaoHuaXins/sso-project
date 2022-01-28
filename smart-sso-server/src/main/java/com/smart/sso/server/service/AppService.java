package com.smart.sso.server.service;

import com.smart.sso.client.rpc.Result;

/**
 * 应用服务接口
 */
public interface AppService {

	boolean exists(String appId);
	
	Result<Void> validate(String appId, String appSecret);
}
