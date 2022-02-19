package com.smart.sso.demo.config;

import com.smart.sso.demo.config.interceptor.RefererInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.*;

import java.util.concurrent.TimeUnit;


/**
 * @Author xhx
 * @Date 2022/2/14 18:56
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Autowired
    RefererInterceptor refererInterceptor;
    @Value("path.file")
    private String filePath;
    @Value("path.photo")
    private String photoPath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(refererInterceptor)
                .addPathPatterns("/**");
    }

    /**
     * 静态资源配置
     * 图片信息不经常更该使用强制缓存 1h
     * 其他静态资源采用协商缓存
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/photo/*.*")
                .addResourceLocations(photoPath)
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS));
        registry.addResourceHandler("/static/file/*.*")
                .addResourceLocations(photoPath);
    }

    //TODO 跨域配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");
    }
}
