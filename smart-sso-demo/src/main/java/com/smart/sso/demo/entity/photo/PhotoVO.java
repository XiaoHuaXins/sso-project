package com.smart.sso.demo.entity.photo;

/**
 * @Author xhx
 * @Date 2022/1/28 17:21
 */
public class PhotoVO {
    private int id;
    private String uri;

    public PhotoVO() {
    }

    public PhotoVO(int id, String uri) {
        this.id = id;
        this.uri = uri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
