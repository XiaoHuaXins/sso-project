package com.smart.sso.demo.aop;

import com.smart.sso.client.rpc.SsoUser;
import com.smart.sso.client.util.SessionUtils;
import com.smart.sso.demo.constant.RedisConstant;
import com.smart.sso.demo.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Author xhx
 * @Date 2022/2/17 21:43
 * 查看当前用户在redis中的权限
 * 如有：则执行方法
 * 否则直接返回
 */
@Component
@Aspect
@Slf4j
public class AuthCheckAdvice {
    @Autowired
    RedisTemplate redisTemplate;

    //private final String path = "redis/check.lua";
    @Pointcut("@annotation(com.smart.sso.demo.aop.AuthCheck)")
    public void authCheck(){}
    //从redis中获取权限信息
    @Before("authCheck()")
    public void getAuth(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        int grade = signature.getMethod().getAnnotation(AuthCheck.class).grade();

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        SsoUser user = SessionUtils.getUser(requestAttributes.getRequest());
        if(user == null)return  ;
        Integer auth = (Integer) redisTemplate.opsForHash().get(RedisConstant.USER_KEY + ":" + RedisConstant.USER_AUTH, user.getId());
        if(auth == null)return;
        if((auth & (1 << grade) ) == 1)point.proceed();
    }

}
