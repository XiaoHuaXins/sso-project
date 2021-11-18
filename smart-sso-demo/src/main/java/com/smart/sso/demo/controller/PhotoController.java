package com.smart.sso.demo.controller;

import com.smart.sso.demo.entity.photo.PhotoInfo;
import com.smart.sso.demo.entity.vo.Result;
import com.smart.sso.demo.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author xhx
 * @Date 2021/11/18 15:30
 */
@RequestMapping("/photo")
@RestController
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @RequestMapping("/index")
    public Result<List<PhotoInfo>> getPhotoInfo() {
        return Result.createSuccess(photoService.getIndexPhotoInfo());
    }

    @RequestMapping("/getMore")
    public Result<List<PhotoInfo>> getMorePhotoInfo(@RequestParam("offset")Integer offset) {
        return Result.createSuccess(photoService.getIndexPhotoInfo());
    }
}
