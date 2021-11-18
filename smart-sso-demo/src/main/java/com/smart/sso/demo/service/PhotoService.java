package com.smart.sso.demo.service;

import com.smart.sso.demo.entity.photo.PhotoInfo;

import java.util.List;

/**
 * @Author xhx
 * @Date 2021/11/18 15:47
 */
public interface PhotoService {

    List<PhotoInfo> getIndexPhotoInfo();

    List<PhotoInfo> getMorePhotoInfo(int offset);
}
