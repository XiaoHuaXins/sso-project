package com.smart.sso.demo.constant;

/**
 * @Author xhx
 * @Date 2022/2/11 9:41
 */
public enum PermissionEnum {
    /**
     * VIEW: 普通访问页面权限，基本无法实现任何功能操作哟
     * UPLOAD: 上传图片权限
     * SPECIAL: 特定的功能权限
     * REVIEW: 审核图片权限
     */
    VIEW(1),
    UPLOAD(2),
    SPECIAL(3),
    REVIEW(4);

    private Integer permit;
    PermissionEnum(Integer permit) {
        this.permit = permit;
    }
}
