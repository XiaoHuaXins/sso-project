package com.smart.sso.demo.utils;

import com.smart.sso.demo.dao.permit.PermitDao;
import com.smart.sso.demo.entity.dto.PermitDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xhx
 * @Date 2022/2/11 10:34
 */
//TODO 完善cookie中的权限信息，并使用正则提取url，并判断该用户是否具有该权限
public class AuthUtil {
    private static Map<String, Integer> auth = new HashMap<>();
    static {
        PermitDao permitDao = SpringContextHolder.getBean("permitDao");
        List<PermitDTO> allPermission = permitDao.findAllPermission();
        for (PermitDTO cur: allPermission) {
            auth.put(cur.getTitle(), cur.getPermissionId());
        }
    }

    public static boolean authUrl(String url){
        return true;
    }
}
