package com.smart.sso.demo.entity.photo;

import com.smart.sso.demo.dao.photo.PhotoInfoDao;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author xhx
 * @Date 2022/1/27 19:26
 *  后台线程持续生成photoinfo
 */
@Slf4j
public class SubscribeImage implements Runnable{

    private BlockingQueue<PhotoJob> images;
    private PhotoInfoDao dao;
    public SubscribeImage(BlockingQueue<PhotoJob> images, PhotoInfoDao dao) {
        this.images = images;
        this.dao = dao;
    }
    @Override
    public void run() {
        try {
            while(true) {
                PhotoJob job = images.take();
                Object call = job.call();
                if(call == null) {
                    return;
                }
                PhotoInfo newInfo = (PhotoInfo) call;
                dao.createNewImage(newInfo);
                log.info("照片信息处理成功！！！");
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
