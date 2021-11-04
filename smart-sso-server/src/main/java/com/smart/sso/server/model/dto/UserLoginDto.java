package com.smart.sso.server.model.dto;

/**
 * @Author xhx
 * @Date 2021/11/1 18:04
 */

public class UserLoginDto {
    private Integer userId;
    private String userName;
    private String password;
    private String email;
    private String userAuth;

    public UserLoginDto(Integer userId, String password, String userAuth) {
        new UserLoginDto(userId, null, password, null, userAuth);
    }

    public UserLoginDto(Integer userId, String userName, String password, String email, String userAuth) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.userAuth = userAuth;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(String userAuth) {
        this.userAuth = userAuth;
    }
}
