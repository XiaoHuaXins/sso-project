package com.smart.sso.demo.service.impl;

import com.google.common.cache.Cache;
import com.smart.sso.demo.constant.ResultEnum;
import com.smart.sso.demo.dao.catalogue.CatalogueDao;
import com.smart.sso.demo.dao.photo.PhotoInfoDao;
import com.smart.sso.demo.dao.userinfo.UserInfoDao;
import com.smart.sso.demo.entity.catalogue.Catalogue;
import com.smart.sso.demo.entity.photo.PhotoInfo;
import com.smart.sso.demo.entity.photo.PhotoJob;
import com.smart.sso.demo.entity.photo.PhotoVO;
import com.smart.sso.demo.entity.photo.SubscribeImage;
import com.smart.sso.demo.entity.user.UserInfo;
import com.smart.sso.demo.service.PhotoService;
import com.smart.sso.demo.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

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
    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    RedisTemplate redisTemplate;

    @Value("${file.path.finish}")
    String filePath;
    @Value("${file.path.section}")
    String sectionPath;

    public final int FIRST_PAGE_SIZE = 20;
    public final int PAGE_INCREMENT = 20;
    private static final int QUEUE_SIZE = 10;
    private final String redisRankString = "rankPhoto";
    private static BlockingQueue<FutureTask> asyncResult = new LinkedBlockingDeque<>(QUEUE_SIZE);


    @Override
    public List<PhotoInfo> getIndexPhotoInfo() {
        return photoInfoDao.getPhotoInfoOnNumber(FIRST_PAGE_SIZE);
    }

    /**
     * 查看全部的photoinfo
     * 并使用offset优化数据库搜索
     * @param offset
     * @return
     */
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
        PhotoInfo newInfo = new PhotoInfo(image.getOriginalFilename(), filePath,1,1, TimeUtils.getUTCTime(),0,bufferedImage.getWidth(),bufferedImage.getHeight());
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

    /**
     * 每点击一个图片就为这张图片增加一定的热度
     * 并将该热度信息缓存至redis，方便以后做排行榜！！！
     * 一天更新一次排行榜
     * @param info
     * @return
     */
    //TODO 使用Lua你脚本单线程，检查容量问题，避免内存爆炸
    @Override
    public void incrPopularity(PhotoVO info) {
        try {
            redisTemplate.multi();
            redisTemplate.opsForZSet().incrementScore(redisRankString, info, 10);
            redisTemplate.expire(redisRankString,12, TimeUnit.HOURS);
            redisTemplate.exec();
        } catch (Exception e) {
            log.info("redis出问题啦！！！");
            e.printStackTrace();
        }
    }

    @Override
    public List<PhotoInfo> getInfoByFuzzyName(String fuzzyName) {
        return  photoInfoDao.FindPhotoInfoByFuzzSearch(fuzzyName);
    }

    /**
     * 要么从缓存中湖区PhotoInfo，要么从数据库中查询
     * @param id
     * @return
     */
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

    /**
     * 通过自定义线程池，将体积较小的图片通过线程池直接存储到本地
     * @param file
     * @return
     */
    @Override
    public UploadResult createSmallImage(MultipartFile file)  {
        if(file.getSize() > 1024 * 1024) {
            //TODO 进入分片上传逻辑
        }else{
            try {
                PhotoJob job = new PhotoJob(file, redisTemplate);
                FutureTask futureTask = new FutureTask(job);
                threadPoolTaskExecutor.submit(futureTask);
                asyncResult.put(futureTask);
            }catch (InterruptedException e) {
                log.info("服务器出错啦-> createImage");
            }
        }
        return UploadResult.builder().code(200).name(ResultEnum.OP_SUCCESS.getCodeMessage()).build();
    }

    @Override
    public Set<PhotoVO> getRedisRank() {
        Set<PhotoVO> set = redisTemplate.opsForZSet().reverseRange(redisRankString, 0, 49);
        return set;
    }

    @PostConstruct
    public void initSubscriber(){
        Thread t = new Thread(new SubscribeImage(asyncResult, photoInfoDao));
        t.start();
    }
}
