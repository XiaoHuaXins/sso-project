package com.smart.sso.demo.entity.dto;

/**
 * @Author xhx
 * @Date 2022/2/11 10:25
 */
public class PermitDTO {
    Integer permissionId;
    String title;

    public PermitDTO() {
    }

    public PermitDTO(Integer permissionId, String title) {
        this.permissionId = permissionId;
        this.title = title;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
