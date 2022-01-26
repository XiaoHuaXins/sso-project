package com.smart.sso.demo.utils;

import lombok.Cleanup;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * @Author xhx
 * @Date 2021/11/10 19:34
 */
public class FileConstant {
    public static final String BASE_PATH = "c:/test-upload/finished";

    public static boolean mergeFile(String[] fpaths, String resultPath) {
        if (fpaths == null || fpaths.length < 1 || StringUtils.isBlank(resultPath)) {
            return false;
        }

        if(fpaths.length == 1) {
            return new File(fpaths[0]).renameTo(new File(resultPath));
        }

        File[] files = new File[fpaths.length];
        for(int i = 0; i < fpaths.length; i++) {
            files[i] = new File(fpaths[i]);
            if (StringUtils.isBlank(fpaths[i]) || !files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }

        File resultFile = new File(resultPath);

        try (FileOutputStream fout = new FileOutputStream(resultFile,true);
                FileChannel resultFileChannel  = fout.getChannel()) {
            for (int i = 0; i < files.length; i++) {
                @Cleanup
                FileInputStream fin = new FileInputStream(files[i]);
                @Cleanup
                FileChannel fileChannel = fin.getChannel();
                resultFileChannel.transferFrom(fileChannel, resultFileChannel.size(),fileChannel.size());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        for (int i = 0; i < fpaths.length; i++) {
            files[i].delete();
        }
        return true;
    }
}
