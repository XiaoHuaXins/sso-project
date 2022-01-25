package com.smart.sso.demo.controller;

import com.google.common.cache.CacheBuilder;
import com.smart.sso.client.rpc.SsoUser;
import com.smart.sso.client.util.SessionUtils;
import com.smart.sso.demo.entity.photo.PhotoInfo;
import com.smart.sso.demo.entity.user.UserInfo;
import com.smart.sso.demo.entity.vo.Result;
import com.smart.sso.demo.service.CatalogueService;
import com.smart.sso.demo.service.PhotoService;
import com.smart.sso.demo.service.UserService;
import com.smart.sso.demo.utils.CacheUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author xhx
 * @Date 2021/11/18 15:30
 */

@RestController("/photo")
public class PhotoController {

    @Autowired
    PhotoService photoService;
    @Autowired
    UserService userService;
    @Autowired
    CatalogueService catalogueService;

    /**
     * 根据爱好字段使用KMP匹配数据库所有类别：
     *  1. 匹配率在50%以上的，推荐该种类别的30张图片
     *  2. 低于50%的，随机抽取三种类别，各推荐10张。
     * @return
     */
    @RequestMapping("/index")
    public Result<List<PhotoInfo>> getPhotoInfo(HttpServletRequest request) {
        SsoUser user = SessionUtils.getUser(request);
        List<PhotoInfo> photoInfos = photoService.cachingFavoriteClass(user.getId());
        return Result.createSuccess(photoInfos);
    }

    /**
     * 获取用户的基本信息：
     *  1. 用户第一次使用该账号登陆 status = 0
     *   2). 需要前端获取用户数据
     *  2. 用户在数据库已经有了记录 status
     * @param request
     * @return
     */
    @RequestMapping("/userinfo")
    public Result userInfo(HttpServletRequest request) {
        SsoUser user = SessionUtils.getUser(request);
        UserInfo userInfoById = userService.findUserInfoById(user.getId());
        if(userInfoById == null) {
            return Result.create(user);
        }else {
            return Result.createSuccess(user);
        }
    }

    /**
     * 获取更多的图片信息
     * @param offset
     * @return
     */
    @RequestMapping("/getMore")
    public Result<List<PhotoInfo>> getMorePhotoInfo(@RequestParam("offset")Integer offset) {
        return Result.createSuccess(photoService.getIndexPhotoInfo());
    }

    /**
     * 尽量使用倒排索引实现
     * @param fuzzyName
     * @return
     */
    @RequestMapping("/findPhotoByName")
    public Result<List<PhotoInfo>> findPhotos(@Param("fuzzyName") String fuzzyName) {

        return Result.createSuccess(photoService.getInfoByFuzzyName(fuzzyName));
    }

    /**
     * 查看某一张图片
     * @param name
     * @return
     */
    @RequestMapping("/click")
    public Result<PhotoInfo> clinkPhoto(@Param("name") String name) {

        return Result.createSuccess(photoService.findPhotoByNameAndCaching(name));
    }
}
