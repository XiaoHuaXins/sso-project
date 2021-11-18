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
}
