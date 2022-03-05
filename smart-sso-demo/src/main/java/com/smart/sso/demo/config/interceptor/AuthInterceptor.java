package com.smart.sso.demo.config.interceptor;

import com.smart.sso.client.rpc.SsoUser;
import com.smart.sso.client.util.SessionUtils;
import com.smart.sso.demo.aop.AuthCheck;
import com.smart.sso.demo.constant.RedisConstant;
import com.smart.sso.demo.service.UserService;
import com.smart.sso.demo.utils.SpringContextHolder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author xhx
 * @Date 2022/2/14 21:25
 * 权限识别
 * 检验redis中该用户的权限信息
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RedisTemplate redisTemplate = SpringContextHolder.getBean(RedisTemplate.class);
        SsoUser user = SessionUtils.getUser(request);
        Integer userAuth = (Integer) redisTemplate.opsForHash().get(RedisConstant.USER_KEY + ":" + RedisConstant.USER_AUTH,user.getId());
        HandlerMethod method = (HandlerMethod) handler;
        AuthCheck check = method.getMethodAnnotation(AuthCheck.class);
        if(null != check) {
            int grade = check.grade();
            if(((1 << grade) & userAuth) == 0)return false;
        }
        return true;
    }
}
