package com.smart.sso.demo.config.interceptor;

import com.smart.sso.demo.aop.AuthCheck;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author xhx
 * @Date 2022/2/14 21:25
 * 权限识别
 * 检验token中的权限是否合法
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        AuthCheck check = method.getMethodAnnotation(AuthCheck.class);
        if(null != check) {
            int grade = check.grade();

        }
        return true;
    }
}
