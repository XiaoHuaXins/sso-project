package com.smart.sso.demo.dao.photo;


import com.smart.sso.demo.entity.photo.PhotoInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

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
     *根据offset加载更多的图片信息
     */
    List<PhotoInfo> getPhotoOnOffset(@Param("photoId") int photoId, @Param("number") int number);

    int createNewImage(PhotoInfo info);

    List<PhotoInfo> FindPhotoInfoByFuzzSearch(@Param("name") String name);

    PhotoInfo FindPhotoInfoByName(@Param("name") String name);

    /**
     * 根据分类id，获取相应数量的info
     * @param classifyId
     * @param n
     * @return
     */
    List<PhotoInfo> findPhotoByCatalogue(@Param("classifyId")int classifyId, @Param("n")int n);
}
