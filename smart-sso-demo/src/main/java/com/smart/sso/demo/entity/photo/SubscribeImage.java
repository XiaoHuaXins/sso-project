package com.smart.sso.demo.entity.photo;

import com.smart.sso.demo.dao.photo.PhotoInfoDao;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @Author xhx
 * @Date 2022/1/27 19:26
 *  后台线程持续生成photoinfo
 */
@Slf4j
public class SubscribeImage implements Runnable{

    private BlockingQueue<FutureTask> images;
    private PhotoInfoDao dao;
    public SubscribeImage(BlockingQueue<FutureTask> images, PhotoInfoDao dao) {
        this.images = images;
        this.dao = dao;
    }
    @Override
    public void run() {
        try {
            while(true) {
                FutureTask job = images.take();
                Object call = job.get();
                if(call == null) {
                    log.info("图片转储到本地不成功！");
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
