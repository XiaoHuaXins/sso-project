package com.smart.sso.server.dao.appinfo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author xhx
 * @Date 2021/11/5 20:15
 *
 * 获取app的信息
 */
@Mapper
@Repository
public interface AppInfoDao {
    String findAppSecretByAppId(@Param("appId") String appId);
}
