package com.smart.sso.demo.config.filter;

import com.smart.sso.client.rpc.SsoUser;
import com.smart.sso.client.session.SessionAccessToken;
import com.smart.sso.client.util.SessionUtils;
import com.smart.sso.demo.constant.HTTPConstant;
import com.smart.sso.demo.entity.user.UserInfo;
import com.smart.sso.demo.service.UserService;
import com.smart.sso.demo.utils.SpringContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @Author xhx
 * @Date 2022/2/14 23:10
 * 验证用户状态：
 * 1. 如果有则放行
 * 2. 否生生成一个token后放行
 */

public class UserInfoFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        UserService userService = SpringContextHolder.getBean(UserService.class);
        SsoUser user = SessionUtils.getUser((HttpServletRequest) servletRequest);
        UserInfo userInfo = userService.findUserInfoById(user.getId());
        if(userInfo == null) {
            redirectRegister((HttpServletResponse) servletResponse, (HttpServletRequest) servletRequest);
        }
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
