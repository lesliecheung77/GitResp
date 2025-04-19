package com.msb.apipassenger.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Bean
    public JwtInterceptor JwtInterceptorInitial(){
        return new JwtInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(JwtInterceptorInitial()).addPathPatterns("/**").
                excludePathPatterns("/noauth"). //测试拦截tokne
                excludePathPatterns("/verification-code").  //放行获取验证码
                excludePathPatterns("/verification-code-check"). //放行校验验证码
                excludePathPatterns("/refresh-token");    //放行refreshToken

    }
}
