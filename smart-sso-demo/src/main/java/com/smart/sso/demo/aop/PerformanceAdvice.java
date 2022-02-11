package com.smart.sso.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author xhx
 * @Date 2022/2/9 9:55
 */
@Aspect
@Component
@Slf4j
public class PerformanceAdvice {

    @Pointcut("@annotation(com.smart.sso.demo.aop.PerformanceAnalysis)")
    public void analysisPerformance(){}

    @Around("analysisPerformance()")
    public void handlerGlobalResult(ProceedingJoinPoint point) throws Throwable {
        long s = System.nanoTime();
        point.proceed();
        long c = System.nanoTime() - s;
        log.info("该方法执行时间为：" + TimeUnit.NANOSECONDS.toSeconds(c));
    }
}
