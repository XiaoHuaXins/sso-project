package com.smart.sso.demo.utils;

import sun.misc.BASE64Encoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author xhx
 * @Date 2022/1/27 21:55
 */
public class MD5Utils {

    public static String getImageMD5(byte[] file) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder encoder = new BASE64Encoder();
            return  encoder.encode(file);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
