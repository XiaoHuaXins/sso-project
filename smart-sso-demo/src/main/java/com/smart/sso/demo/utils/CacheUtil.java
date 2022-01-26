package com.smart.sso.demo.utils;

import com.google.common.cache.*;
import com.smart.sso.demo.entity.photo.PhotoInfo;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author xhx
 * @Date 2021/11/25 21:45
 */
@Slf4j
public class CacheUtil {
//    @Autowired
//    PhotoInfoDao photoInfoDao;
    public static final long MAX_CACHE_SIZE =  100_000 ;

    //定义guava中cache的配置
    public static Cache<Integer, List<PhotoInfo>> photoCache = CacheBuilder.newBuilder()
            .initialCapacity(50)
            //缓存池大小，在存项接近该大小时，Guava开始回收缓存
            .maximumSize(MAX_CACHE_SIZE)
            //设置过期策略
            .expireAfterWrite(60, TimeUnit.MINUTES)
            //设置刷新策略
            .expireAfterAccess(60, TimeUnit.MINUTES)
            //并发更新数
            .concurrencyLevel(5)
            //移除触发监听器
            .removalListener(entry -> {
                log.info("已经被移除{}!" , entry.getKey());
            })
            //.maximumWeight(MAX_CACHE_SIZE)
//            .weigher((Weigher<String, PhotoInfo>) (s, info) -> info.getTemperature())
            .build();
}
