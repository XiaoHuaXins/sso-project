package com.smart.sso.demo.entity.user;

import lombok.Data;

/**
 * @Author xhx
 * @Date 2022/1/24 18:39
 */

public class UserInfo {
    private int userId;
    private int phone;
    private String preference;
    private int roleId;
    private boolean status;

    public UserInfo(int userId, int phone, String preference, int roleId, boolean status) {
        this.userId = userId;
        this.phone = phone;
        this.preference = preference;
        this.roleId = roleId;
        this.status = status;
    }

    public UserInfo() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
