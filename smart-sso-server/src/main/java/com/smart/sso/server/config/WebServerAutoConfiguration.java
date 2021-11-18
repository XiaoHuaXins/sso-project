package com.smart.sso.server.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @Author xhx
 * @Date 2021/11/2 15:41
 *
 * 自定义SpringBoot 错误异常
 */

public class WebServerAutoConfiguration {
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        ErrorPage errorPage_400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400");
        return factory;
    }
}
