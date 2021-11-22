package com.smart.sso.demo.controller;

import com.smart.sso.demo.constant.FileConstant;
import com.smart.sso.demo.service.PhotoService;
import com.smart.sso.demo.utils.UploadParam;
import com.smart.sso.demo.utils.UploadResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Author xhx
 * @Date 2021/11/10 19:08
 */
@RestController
@CrossOrigin
@RequestMapping("/file")
@Slf4j
public class FileUploadController {


    @Autowired
    PhotoService photoService;
    @RequestMapping("/test")
    public UploadResult imageUpload(MultipartFile image) throws IOException {
        return photoService.createNewImage(image);
    }

    //TODO 断点续传的数据结构
    @RequestMapping("/sectionUpload")
    public UploadResult uploadFile(MultipartFile file, UploadParam uploadParam) {
        String task = uploadParam.getIdentifier();
        int seq = uploadParam.getChunkNumber();
        String fileName = String.format("%s%d", task, seq);
        log.info("文件名为：{}",fileName);
        try {
            file.transferTo(new File(String.format("c:/upload/%s.jpg",fileName)));
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
            log.error("转移错误！");
        }
        return UploadResult.builder().name(fileName).build();
    }

    @RequestMapping("/mergeSection")
    public UploadResult mergeFile(@RequestParam("identifier") String identifier, String fileName,
                                  int totalChunks) {
        // 组装所有文件的路径
        String[] paths = new String[totalChunks];// 存放所有路径
        for (int chunk = 1 /* 分片开始序号 */; chunk <= totalChunks; chunk++) {
            paths[chunk - 1] = FileConstant.BASE_PATH + identifier + chunk;
        }

        return UploadResult.builder().code(200).build();
    }
}
