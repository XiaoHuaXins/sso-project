package com.smart.sso.demo.constant;

import lombok.Data;

/**
 * @Author xhx
 * @Date 2021/11/18 15:36
 */

public enum ResultEnum {
    OP_SUCCESS(0, "操作成功！"),
    OP_FAILED(1,"操作失败");
    private Integer status;
    private String codeMessage;
    ResultEnum(Integer status, String codeMessage) {
        this.status = status;
        this.codeMessage = codeMessage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCodeMessage() {
        return codeMessage;
    }

    public void setCodeMessage(String codeMessage) {
        this.codeMessage = codeMessage;
    }
}
