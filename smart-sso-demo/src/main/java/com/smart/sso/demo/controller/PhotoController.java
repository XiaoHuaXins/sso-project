package com.smart.sso.demo.controller;

import com.smart.sso.demo.entity.photo.PhotoInfo;
import com.smart.sso.demo.entity.vo.Result;
import com.smart.sso.demo.service.PhotoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author xhx
 * @Date 2021/11/18 15:30
 */

@RestController
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @RequestMapping("/index")
    public Result<List<PhotoInfo>> getPhotoInfo() {
        return Result.createSuccess(photoService.getIndexPhotoInfo());
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
     * 只支持前缀模糊查询
     * @param fuzzyName
     * @return
     */
    @RequestMapping("/findPhotoByName")
    public Result<List<PhotoInfo>> findPhotos(@Param("fuzzyName") String fuzzyName) {
        return Result.createSuccess(photoService.getInfoByFuzzyName(fuzzyName));
    }

    @RequestMapping("/clinck")
    public Result<PhotoInfo> clinkPhoto(@Param("name") String name) {
        return Result.createSuccess(photoService.findPhotoByNameAndCaching(name));
    }
}
