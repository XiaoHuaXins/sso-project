package com.smart.sso.demo.entity.photo;

/**
 * @Author xhx
 * @Date 2022/1/28 17:21
 */
public class PhotoVO {
    private int id;
    //点赞数
    private long likes;
    //点赞状态
    private boolean status;

    public PhotoVO() {
    }

    public PhotoVO(int id, String uri) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PhotoVO(int id, long likes, boolean status) {
        this.id = id;
        this.likes = likes;
        this.status = status;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
