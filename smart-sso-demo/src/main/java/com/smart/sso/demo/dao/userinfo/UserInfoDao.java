package com.smart.sso.demo.dao.userinfo;

import com.smart.sso.demo.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author xhx
 * @Date 2022/1/24 18:46
 */
@Mapper
public interface UserInfoDao {

    UserInfo findUserInfoById(@Param("id")int id);

    int addUserInfo(@Param("info")UserInfo info);

}
