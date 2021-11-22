package com.smart.sso.demo.service.impl;

import com.smart.sso.demo.dao.photo.PhotoInfoDao;
import com.smart.sso.demo.entity.photo.PhotoInfo;
import com.smart.sso.demo.service.PhotoService;
import com.smart.sso.demo.utils.UploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Author xhx
 * @Date 2021/11/18 15:48
 */
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    PhotoInfoDao photoInfoDao;

    @Value("${file.path.finish}")
    String filePath;
    @Value("${file.path.section}")
    String sectionPath;

    public final int FIRST_PAGE_SIZE = 20;
    public final int PAGE_INCREMENT = 20;


    @Override
    public List<PhotoInfo> getIndexPhotoInfo() {
        return photoInfoDao.getPhotoInfoOnNumber(FIRST_PAGE_SIZE);
    }

    @Override
    public List<PhotoInfo> getMorePhotoInfo(int offset) {
        return photoInfoDao.getPhotoOnOffset(offset, PAGE_INCREMENT);
    }

    @Override
    //todo 删除图片的逻辑！
    /**
     * 如果数据库记录插入失败，图片应该删除掉！
     */
    public UploadResult createNewImage(MultipartFile image) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
        if(bufferedImage == null) {
            return UploadResult.builder().name("图片名重复！").code(1).build();
        }
        PhotoInfo newInfo = new PhotoInfo(image.getOriginalFilename(), filePath,1,1,getUTCTime(),0,bufferedImage.getWidth(),bufferedImage.getHeight());
        photoInfoDao.createNewImage(newInfo);
        try {
            image.transferTo(new File(filePath + File.separator + image.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
            return UploadResult.builder().name("图片名重复！").code(1).build();
        }
        return UploadResult.builder().name("成功").code(0).build();
    }

    private String getUTCTime() {
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return pattern.format(now);
    }
}
