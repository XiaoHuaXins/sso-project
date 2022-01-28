package com.smart.sso.demo.entity.photo;

import com.smart.sso.demo.constant.FilesConstant;
import com.smart.sso.demo.dao.photo.PhotoInfoDao;
import com.smart.sso.demo.utils.MD5Utils;
import com.smart.sso.demo.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

/**
 * @Author xhx
 * @Date 2022/1/26 16:45
 * 后台单线程需要处理的任务：
 * 1. 检查该格式是否为图片的格式
 * 2. 检查图片名的摘要，看是否有重复（可改进）
 * 3. 向数据库中插入新的图片信息
 */
@Slf4j
public class PhotoJob implements Callable {
    MultipartFile in;

    final String filePath = FilesConstant.FINISH_PATH;
    private RedisTemplate redisTemplate;
    private final String redisPhotoMD5Key = "photoSummary";
    public PhotoJob(MultipartFile file, RedisTemplate redisTemplate){
        in = file;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Object call() throws Exception {
        try {
            //检查该多媒体文件是否是图片
            BufferedImage bufferedImage = ImageIO.read(in.getInputStream());
            if(bufferedImage == null) return null;
            String newName = MD5Utils.getImageMD5(in.getOriginalFilename().getBytes(StandardCharsets.UTF_8));
            Long add = redisTemplate.opsForSet().add(redisPhotoMD5Key, newName);
            if(add == 0)return null;
            //TODO 需要使用正则截图.后面的文件格式
            in.transferTo(new File(filePath + File.separator + newName + ".jpg"));
            PhotoInfo newInfo = new PhotoInfo(newName, filePath,1,1, TimeUtils.getUTCTime(),0,bufferedImage.getWidth(),bufferedImage.getHeight());
            return newInfo;
        } catch (IOException e) {
            e.printStackTrace();
            log.info("上传图片失败了！！！");
            return null;
        }
    }
}
