package com.smart.sso.demo.utils;

import lombok.Builder;

/**
 * @Author xhx
 * @Date 2021/11/10 19:13
 */
@Builder
public class UploadResult {
    private String name;
    private int code;

    public UploadResult() {
    }

    public UploadResult(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
