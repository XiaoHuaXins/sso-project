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
import org.springframework.data.redis.core.script.DefaultRedisScript;
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
import java.util.concurrent.locks.ReadWriteLock;

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
    private final String clinkScriptPath = "";
    private final String likesScriptPath = "";

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
     * ???????????????photoinfo
     * ?????????offset?????????????????????
     * @param offset
     * @return
     */
    @Override
    public List<PhotoInfo> getMorePhotoInfo(int offset) {
        return photoInfoDao.getPhotoOnOffset(offset, PAGE_INCREMENT);
    }

    @Override
    /**
     * ????????????????????????????????????????????????????????????
     */
    @Transactional
    public UploadResult createNewImage(MultipartFile image) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
        if(bufferedImage == null) {
            return UploadResult.builder().name("??????????????????").code(1).build();
        }
        PhotoInfo newInfo = new PhotoInfo(image.getOriginalFilename(), filePath,1,1, TimeUtils.getUTCTime(),0,bufferedImage.getWidth(),bufferedImage.getHeight());
        int newImage = photoInfoDao.createNewImage(newInfo);
        if(newImage == 0) {
            return UploadResult.builder().name("??????").code(1).build();
        }
        try {
            image.transferTo(new File(filePath + File.separator + image.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
            return UploadResult.builder().name("??????????????????").code(1).build();
        }
        return UploadResult.builder().name("??????").code(0).build();
    }

    /**
     * ????????????????????????????????????????????????????????????
     * ??????????????????????????????redis????????????????????????????????????
     * ??????lua???????????????????????????????????????????????????????????????
     * @param info
     * @return
     */
    //TODO ????????????????????????????????????????????????redis????????????????????????????????????????????????????????????
    @Override
    public PhotoVO incrPopularity(int info, int userId) {
        try {
            DefaultRedisScript clinkScript = RedisUtil.getScript(Long[].class, clinkScriptPath);
            Long[] res = (Long[]) redisTemplate.execute(clinkScript, Arrays.asList(redisRankString, info, userId));
            PhotoVO vo = new PhotoVO(info, res[0], res[1] == 1);
            return vo;
        } catch (Exception e) {
            log.info("redis?????????????????????");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PhotoInfo> getInfoByFuzzyName(String fuzzyName) {
        return  photoInfoDao.FindPhotoInfoByFuzzSearch(fuzzyName);
    }

    /**
     * ????????????????????????PhotoInfo??????????????????????????????
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
            //????????????????????????????????????????????????????????????????????????
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
            return ifPresent;
    }

    @Override
    public List<PhotoInfo> getDesignatedCataloguePhotoInfo(int catalogueId, int num) {
        return null;
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????????????????
     * @param file
     * @return
     */
    @Override
    public UploadResult createSmallImage(MultipartFile file)  {
        if(file.getSize() > 1024 * 1024) {
            //TODO ????????????????????????
        }else{
            try {
                PhotoJob job = new PhotoJob(file, redisTemplate);
                FutureTask futureTask = new FutureTask(job);
                threadPoolTaskExecutor.submit(futureTask);
                asyncResult.put(futureTask);
            }catch (InterruptedException e) {
                log.info("??????????????????-> createImage");
            }
        }
        return UploadResult.builder().code(200).name(ResultEnum.OP_SUCCESS.getCodeMessage()).build();
    }

    @Override
    public Set<PhotoVO> getRedisRank() {
        Set<PhotoVO> set = redisTemplate.opsForZSet().reverseRange(redisRankString, 0, 49);
        return set;
    }

    @Override
    public void modifyStatus(int userId, int photoId) {
        DefaultRedisScript clinkScript = RedisUtil.getScript(Void.class, likesScriptPath);
    }

    @PostConstruct
    public void initSubscriber(){
        Thread t = new Thread(new SubscribeImage(asyncResult, photoInfoDao));
        t.start();
    }
}
