package com.smart.sso.server.service.login;

/**
 * @Author xhx
 * @Date 2021/11/1 21:49
 */
public interface LoginService {
    int addCommonUser(String userName, String password, String email) throws Exception;
}
