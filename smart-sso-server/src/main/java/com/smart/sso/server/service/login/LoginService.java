package com.smart.sso.server.service.login;

/**
 * @Author xhx
 * @Date 2021/11/1 21:49
 */
public interface LoginService {
    /**
     * 增加一个common权限的User
     * @param userName
     * @param password
     * @param email
     * @return
     * @throws Exception
     */
    int addCommonUser(String userName, String password, String email) throws Exception;

}
