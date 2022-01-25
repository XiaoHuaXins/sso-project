package com.smart.sso.demo.service.impl;

import com.google.common.cache.Cache;
import com.smart.sso.demo.dao.catalogue.CatalogueDao;
import com.smart.sso.demo.dao.photo.PhotoInfoDao;
import com.smart.sso.demo.dao.userinfo.UserInfoDao;
import com.smart.sso.demo.entity.catalogue.Catalogue;
import com.smart.sso.demo.entity.photo.PhotoInfo;
import com.smart.sso.demo.entity.user.UserInfo;
import com.smart.sso.demo.service.PhotoService;
import com.smart.sso.demo.utils.CacheUtil;
import com.smart.sso.demo.utils.LocalStringUtils;
import com.smart.sso.demo.utils.RandomUtils;
import com.smart.sso.demo.utils.UploadResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @Author xhx
 * @Date 2021/11/18 15:48
 */
@Service
@Slf4j
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    PhotoInfoDao photoInfoDao;
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    CatalogueDao catalogueDao;

    @Value("${file.path.finish}")
    String filePath;
    @Value("${file.path.section}")
    String sectionPath;

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



    @Override
    /**
     * 如果数据库记录插入失败，图片应该删除掉！
     */
    @Transactional
    public UploadResult createNewImage(MultipartFile image) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
        if(bufferedImage == null) {
            return UploadResult.builder().name("图片名重复！").code(1).build();
        }
        PhotoInfo newInfo = new PhotoInfo(image.getOriginalFilename(), filePath,1,1,getUTCTime(),0,bufferedImage.getWidth(),bufferedImage.getHeight());
        int newImage = photoInfoDao.createNewImage(newInfo);
        if(newImage == 0) {
            return UploadResult.builder().name("失败").code(1).build();
        }
        try {
            image.transferTo(new File(filePath + File.separator + image.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
            return UploadResult.builder().name("图片名重复！").code(1).build();
        }
        return UploadResult.builder().name("成功").code(0).build();
    }

    @Override
    public PhotoInfo findPhotoByNameAndCaching(String name) {
        try {
            //PhotoInfo photoInfo = CacheUtil.photoCache.get(name);
            PhotoInfo photoInfo = new PhotoInfo();
            return photoInfo;
        } catch (Exception e) {
            log.info("cache出问题了！");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PhotoInfo> getInfoByFuzzyName(String fuzzyName) {
        return  photoInfoDao.FindPhotoInfoByFuzzSearch(fuzzyName);
    }

    @Override
    public List<PhotoInfo> cachingFavoriteClass(int id) {
        Cache<Integer, List<PhotoInfo>> photoCache = CacheUtil.photoCache;
        List<PhotoInfo> ifPresent = photoCache.getIfPresent(id);
        if(ifPresent == null) {
            UserInfo userInfoById = userInfoDao.findUserInfoById(id);
            List<Catalogue> allCatalogue = catalogueDao.findAllCatalogue();
            String[] classify =(String[])allCatalogue.stream().map(Catalogue::getDescription).toArray();
            int kmp = LocalStringUtils.kmp(userInfoById.getPreference(), classify);
            //达到指定的匹配率则选取匹配的分类，否则随机分类。
            if(kmp == -1) {
                int nc = Math.min(allCatalogue.size(), 3);
                int[] kruth = RandomUtils.kruth(nc);
                ifPresent = new ArrayList<>();
                for(int i = 0; i < nc; i++) {
                    ifPresent.addAll(photoInfoDao.findPhotoByCatalogue(allCatalogue.get(kruth[i]).getCatalogueId(), 10));
                }
            }else {
                int pageSize = 30;
                ifPresent = photoInfoDao.findPhotoByCatalogue(kmp, pageSize);
            }
        }   photoCache.put(id, ifPresent);
            return  ifPresent;

    }

    @Override
    public List<PhotoInfo> getDesignatedCataloguePhotoInfo(int catalogueId, int num) {
        return null;
    }

    private String getUTCTime() {
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return pattern.format(now);
    }
}
