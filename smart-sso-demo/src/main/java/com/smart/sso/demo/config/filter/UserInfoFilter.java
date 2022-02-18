package com.smart.sso.demo.config.filter;

import com.smart.sso.client.rpc.SsoUser;
import com.smart.sso.client.session.SessionAccessToken;
import com.smart.sso.client.util.SessionUtils;
import com.smart.sso.demo.constant.HTTPConstant;
import com.smart.sso.demo.constant.RedisConstant;
import com.smart.sso.demo.entity.user.UserInfo;
import com.smart.sso.demo.service.UserService;
import com.smart.sso.demo.utils.SpringContextHolder;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @Author xhx
 * @Date 2022/2/14 23:10
 * 验证用户状态：
 * 1. 如果在本地app上有记录则放行
 * 2. 否则默认生成后重新来
 */

public class UserInfoFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        UserService userService = SpringContextHolder.getBean(UserService.class);
        RedisTemplate redisTemplate = SpringContextHolder.getBean(RedisTemplate.class);
        SsoUser user = SessionUtils.getUser((HttpServletRequest) servletRequest);
        UserInfo userInfo = userService.findUserInfoById(user.getId());
        if(userInfo == null) {
            redirectRegister((HttpServletResponse) servletResponse, (HttpServletRequest) servletRequest);
        }
        //TODO 考虑是否要对权限设置过期时间
        redisTemplate.opsForHash().putIfAbsent(RedisConstant.USER_KEY + ":" + RedisConstant.USER_AUTH,userInfo.getUserId(), userInfo.getRoleId());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void redirectRegister(HttpServletResponse response, HttpServletRequest request) {
        try {
            StringBuilder sb = new StringBuilder().append(request.getServerName())
                    .append(HTTPConstant.REGISTER_URL)
                    .append("?")
                    .append(HTTPConstant.REDIRECT_URL).append("=")
                    .append(URLEncoder.encode(request.getRequestURI(),"utf-8"));
            response.sendRedirect(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
