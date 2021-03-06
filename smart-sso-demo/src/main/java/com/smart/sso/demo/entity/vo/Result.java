package com.smart.sso.demo.entity.vo;

import com.smart.sso.demo.constant.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author xhx
 * @Date 2021/11/18 15:34
 */
@Data
@AllArgsConstructor
public class Result<T> {
    private int status;
    private String message;
    private T data;

    public static <T> Result<T> createSuccess(T data) {
        return new Result<>(ResultEnum.OP_SUCCESS.getStatus(), ResultEnum.OP_SUCCESS.getCodeMessage(), data);
    }
    public static <T> Result<T> createSuccess() {
        return new Result<>(ResultEnum.OP_SUCCESS.getStatus(), ResultEnum.OP_SUCCESS.getCodeMessage(), null);
    }

    public static <T> Result<T> create(T data) {
        return new Result<>(ResultEnum.OP_SUCCESS.getStatus(), ResultEnum.FIRST_LOGIN.getCodeMessage(), data);
    }

    public static <T> Result<T> createFailed() {
        return new Result<>(ResultEnum.OP_FAILED.getStatus(), ResultEnum.OP_FAILED.getCodeMessage(), null);
    }
}
