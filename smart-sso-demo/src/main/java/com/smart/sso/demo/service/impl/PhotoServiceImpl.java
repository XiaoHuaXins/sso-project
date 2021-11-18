package com.smart.sso.demo.service.impl;

import com.smart.sso.demo.dao.photo.PhotoInfoDao;
import com.smart.sso.demo.entity.photo.PhotoInfo;
import com.smart.sso.demo.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author xhx
 * @Date 2021/11/18 15:48
 */
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    PhotoInfoDao photoInfoDao;

    public final int FIRST_PAGE_SIZE = 20;
    public final int PAGE_INCREMENT = 20;
    @Override
    public List<PhotoInfo> getIndexPhotoInfo() {
        return photoInfoDao.getPhotoInfoOnNumber(FIRST_PAGE_SIZE);
    }

    @Override
    public List<PhotoInfo> getMorePhotoInfo(int offset) {
        return photoInfoDao.getPhotoOnOffset(offset, PAGE_INCREMENT);
    }
}
