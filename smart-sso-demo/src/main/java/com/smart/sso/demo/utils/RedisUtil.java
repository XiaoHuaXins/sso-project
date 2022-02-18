package com.smart.sso.demo.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @Author xhx
 * @Date 2022/2/18 13:12
 */
public class RedisUtil {

    public static DefaultRedisScript getScript(Class returnType, String path) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(returnType);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));
        return redisScript;
    }
}
