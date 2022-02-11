package com.smart.sso.demo.config.filter;

import com.smart.sso.demo.dao.permit.PermitDao;
import com.smart.sso.demo.service.PermitService;
import com.smart.sso.demo.utils.SpringContextHolder;

import javax.servlet.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xhx
 * @Date 2022/2/11 9:59
 */
public class PermitFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
