package com.smart.sso.demo.entity.photo;

import com.smart.sso.demo.constant.FilesConstant;
import com.smart.sso.demo.dao.photo.PhotoInfoDao;
import com.smart.sso.demo.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @Author xhx
 * @Date 2022/1/26 16:45
 */
@Slf4j
public class PhotoJob implements Callable {
    MultipartFile in;

    final String filePath = FilesConstant.FINISH_PATH;
//    @Autowired
//    PhotoInfoDao photoInfoDao;
    public PhotoJob(MultipartFile file){
        in = file;
    }

    @Override
    public Object call() throws Exception {
        try {
            //检查该多媒体文件是否是图片
            BufferedImage bufferedImage = ImageIO.read(in.getInputStream());
            if(bufferedImage == null) return null;
            log.info(filePath + "-------------" + in.getOriginalFilename());
            String newName = in.getName().hashCode()+"";
            in.transferTo(new File(filePath + File.separator + newName + ".jpg"));
            PhotoInfo newInfo = new PhotoInfo(newName, filePath,1,1, TimeUtils.getUTCTime(),0,bufferedImage.getWidth(),bufferedImage.getHeight());
//            photoInfoDao.createNewImage(newInfo);
            return newInfo;
            //TODO 数据库更新、mapper文件有些要修改
        } catch (IOException e) {
            e.printStackTrace();
            log.info("上传图片失败了！！！");
            return null;
        }
    }
}
