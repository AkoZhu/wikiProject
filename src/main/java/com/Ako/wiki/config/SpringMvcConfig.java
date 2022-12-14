package com.Ako.wiki.config;

//import com.Ako.wiki.interceptor.LogInterceptor;
import com.Ako.wiki.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
// @Configuration
// public class SpringMvcConfig implements WebMvcConfigurer {

    // @Resource
    // LogInterceptor logInterceptor;

    // public void addInterceptors(InterceptorRegistry registry) {
    //     registry.addInterceptor(logInterceptor)
    //             .addPathPatterns("/**").excludePathPatterns("/login");
    // }

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Resource
    LoginInterceptor loginInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/test/**",
                        "/redis/**",
                        "/user/login",
                        "/category/all",
                        "/ebook/list",
                        "/doc/all/**",
                        "/doc/find-content/**",
                        "/doc/**",
                        "/doc/vote/**",
                        "/error",
                        "/vote/**",
                        "/user/logout/**"
                );
    }
}
