package com.smart.sso.demo.aop;

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

/**
 * @Author xhx
 * @Date 2022/2/17 21:43
 */
@Component
@Aspect
@Slf4j
public class AuthCheckAdvice {
    @Autowired
    RedisTemplate redisTemplate;
    @Pointcut("@annotation(com.smart.sso.demo.aop.AuthCheck)")
    public void authCheck(){}
    //从redis中获取权限信息
    @Before("authCheck()")
    public void getAuth(ProceedingJoinPoint point){
        MethodSignature signature = (MethodSignature) point.getSignature();
        int grade = signature.getMethod().getAnnotation(AuthCheck.class).grade();
        //redisTemplate.opsForHash().put();
    }

}
