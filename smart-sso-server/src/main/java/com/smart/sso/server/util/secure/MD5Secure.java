package com.smart.sso.server.util.secure;

import com.smart.sso.server.model.secure.SecureType;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

/**
 * @Author xhx
 * @Date 2021/11/1 16:44
 *
 *  MD5单向加密 数据库字段
 */
@Component
public class MD5Secure extends BaseSecure{

    @Override
    public  byte[] encrypt(byte[] data) throws Exception
    {
        MessageDigest messageDigest = MessageDigest.getInstance(SecureType.MD5.getType());
        return messageDigest.digest(data);
    }

    @Override
    public byte[] decrypt(byte[] data) throws Exception {
        return null;
    }
}
