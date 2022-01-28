package com.smart.sso.demo.service;

import com.smart.sso.demo.entity.photo.PhotoInfo;
import com.smart.sso.demo.entity.photo.PhotoVO;
import com.smart.sso.demo.utils.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @Author xhx
 * @Date 2021/11/18 15:47
 */
public interface PhotoService {

    List<PhotoInfo> getIndexPhotoInfo();

    List<PhotoInfo> getMorePhotoInfo(int offset);

    UploadResult createNewImage(MultipartFile image) throws IOException;

    void incrPopularity(PhotoVO info);

    List<PhotoInfo> getInfoByFuzzyName(String fuzzyName);

    List<PhotoInfo> cachingFavoriteClass(int id);

    List<PhotoInfo> getDesignatedCataloguePhotoInfo(int catalogueId, int num);

    UploadResult createSmallImage(MultipartFile file);

    Set<PhotoVO> getRedisRank();
}
