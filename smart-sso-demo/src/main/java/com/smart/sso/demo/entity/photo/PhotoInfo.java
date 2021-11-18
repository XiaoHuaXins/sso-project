package com.smart.sso.demo.entity.photo;

import lombok.Data;

/**
 * @Author xhx
 * @Date 2021/11/18 15:49
 */
@Data
public class PhotoInfo {
    private int photoId;
    private String photoName;
    private String uri;
    private int topicId;
    private int classifyId;
    private String createTime;
    private int temperature;

    public PhotoInfo(int photoId, String photoName, String uri, int topicId, int classifyId) {
        this.photoId = photoId;
        this.photoName = photoName;
        this.uri = uri;
        this.topicId = topicId;
        this.classifyId = classifyId;
    }

    public PhotoInfo(int photoId, String photoName, String uri, int topicId, int classifyId, String createTime, int temperature) {
        this.photoId = photoId;
        this.photoName = photoName;
        this.uri = uri;
        this.topicId = topicId;
        this.classifyId = classifyId;
        this.createTime = createTime;
        this.temperature = temperature;
    }

    public PhotoInfo() {
    }
}
