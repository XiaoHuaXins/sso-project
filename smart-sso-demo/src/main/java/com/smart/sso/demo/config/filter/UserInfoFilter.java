package com.smart.sso.demo.config.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author xhx
 * @Date 2022/2/14 23:10
 * 验证用户状态：
 * 1. 如果有则放行
 * 2. 否生生成一个token后放行
 */
public class UserInfoFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }
}
