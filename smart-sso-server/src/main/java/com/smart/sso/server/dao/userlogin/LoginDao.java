package com.smart.sso.server.dao.userlogin;

import com.smart.sso.server.model.dto.UserLoginDto;
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

    //TODO 增加权限功能
    /**
     * 增加一个用户， 增加权限
     * @param userId
     * @param password
     * @param email
     * @return
     */
    int insertUser(@Param("userName")Integer userId, @Param("password")String password, @Param("email") String email);

    String findPasswordByUserId(@Param("userId")int userId);
    /**
     *  查询用户登陆状态信息
     */
    UserLoginDto findUser(@Param("userId")Integer userId);

    /**
     * 更新用户在线信息
     * @param userId
     * @return
     */
    Integer updateLoginStatus(@Param("userId")Integer userId);
    /**
     * 更新用户下线信息
     */
    Integer updateLogoutStatus(@Param("userId")Integer userId);
}
