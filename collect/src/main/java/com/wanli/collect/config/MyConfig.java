package com.wanli.collect.config;

import com.wanli.collect.config.interceptor.LoginInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Hu
 * @date 2018/12/25 15:26
 */

@SpringBootConfiguration
public class MyConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    public MyConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    /**
     * 拦截器配置
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/users/login");
    }
}
