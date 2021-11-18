package com.smart.sso.demo.dao.photo;


import com.smart.sso.demo.entity.photo.PhotoInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author xhx
 * @Date 2021/11/18 16:01
 */
@Mapper
public interface PhotoInfoDao {
    /**
     * 默认倒序获取指定数量的图片信息
     * @param number
     * @return
     */
    List<PhotoInfo> getPhotoInfoOnNumber(@Param("number") int number);
    /**
     *
     */
    List<PhotoInfo> getPhotoOnOffset(@Param("photoId") int photoId, @Param("number") int number);
}
