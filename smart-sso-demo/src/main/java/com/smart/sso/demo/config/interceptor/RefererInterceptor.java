package com.smart.sso.demo.config.interceptor;

import com.smart.sso.demo.entity.RefererProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * @Author xhx
 * @Date 2022/2/14 18:35
 */
@Slf4j
@Component
public class RefererInterceptor extends HandlerInterceptorAdapter {

    private AntPathMatcher matcher = new AntPathMatcher();
    @Autowired
    RefererProperties properties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String referer = request.getHeader("referer");
        String host = request.getServerName();
        log.info(referer + " " +host);
        //验证POST请求
        if("POST".equals(request.getMethod())) {
            if(referer == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return false;
            }
            URL url = new URL(referer);
            if(!host.equals(url.getHost())) {
                Set<String> refererDomain = properties.getRefererDomain();
                if(!refererDomain.contains(referer))return false;
            }
        }
        log.info("通过验证！");
        return true;
    }
}
