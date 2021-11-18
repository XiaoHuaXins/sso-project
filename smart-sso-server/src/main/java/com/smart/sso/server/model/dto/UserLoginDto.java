package com.smart.sso.server.model.dto;

import java.util.Date;

/**
 * @Author xhx
 * @Date 2021/11/1 18:04
 */

public class UserLoginDto {
    private Integer userId;
    private String userName;
    private String password;
    private String email;
    private Date createDate;
    private Date lastLoginTime;
    private Boolean enabled;
    private Boolean accountNonLocked;

    public Date getCreateDate() {
        return createDate;
    }

    public UserLoginDto(Integer userId, String userName, String password, String email, Date createDate, Date lastLoginTime, Boolean enabled, Boolean accountNonLocked, Boolean accountNonExpired) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.createDate = createDate;
        this.lastLoginTime = lastLoginTime;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired = accountNonExpired;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    private Boolean accountNonExpired;


    public UserLoginDto(Integer userId, String password, String userAuth) {
        new UserLoginDto(userId, null, password, null, userAuth);
    }

    public UserLoginDto(Integer userId, String userName, String password, String email, String userAuth) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
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
}
