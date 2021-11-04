package com.smart.sso.server.dao.userlogin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @Author xhx
 * @Date 2021/11/1 21:40
 */
@Mapper
@Repository
public interface LoginDao {

    int insertUser(@Param("userName")String userName, @Param("password")String password, @Param("email") String email, @Param("auth")String auth);

    String findPasswordByUserId(@Param("userId")int userId);
}
