package com.smart.sso.server.model.secure;

/**
 * @Author xhx
 * @Date 2021/11/1 16:47
 */
public enum EncType {
    HEX("HEX"),
    BASE64("BASE64"),
    DEFAULT("DEFAULT");//系统默认编码

    private String type;

    EncType(String type){
        this.type=type;
    }

    public String getType(){
        return type;
    }
}
