package com.smart.sso.demo.utils;

import com.google.common.cache.*;
import com.smart.sso.demo.dao.photo.PhotoInfoDao;
import com.smart.sso.demo.entity.photo.PhotoInfo;
import com.smart.sso.demo.service.PhotoService;
import com.smart.sso.demo.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author xhx
 * @Date 2021/11/25 21:45
 */
@Slf4j
public class CacheUtil {
//    @Autowired
//    PhotoInfoDao photoInfoDao;
    public static final long MAX_CACHE_SIZE =  1000 ;

    //定义guava中cache的配置
    public static LoadingCache<String, PhotoInfo> photoCache = CacheBuilder.newBuilder()
            .initialCapacity(50)
            //缓存池大小，在存项接近该大小时，Guava开始回收缓存
            .maximumSize(MAX_CACHE_SIZE)
            //设置过期策略
            .expireAfterWrite(10, TimeUnit.MINUTES)
            //设置刷新策略
            .expireAfterAccess(10, TimeUnit.MINUTES)
            //并发更新数
            .concurrencyLevel(5)
            //移除触发监听器
            .removalListener(entry -> {
                log.info("已经被移除{}!" , entry.getKey());
            })
            .maximumWeight(1_000_000)
            .weigher((Weigher<String, PhotoInfo>) (s, info) -> info.getTemperature()).build(new CacheLoader<String, PhotoInfo>() {
                @Override
                public PhotoInfo load(String s) throws Exception {
                    return null;
                }

                private PhotoInfo getInfo(String name) {
                    log.info("缓存未命中，进入数据库查询");
                    return SpringContextHolder.getBean(PhotoInfoDao.class).FindPhotoInfoByName(name);
                }
            });
}
