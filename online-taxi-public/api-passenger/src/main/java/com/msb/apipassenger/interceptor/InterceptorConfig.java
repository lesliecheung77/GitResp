package com.msb.apipassenger.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/**").
                excludePathPatterns("/noauth"). //测试拦截tokne
                excludePathPatterns("/verification-code").  //放行获取验证码
                excludePathPatterns("/verification-code-check");    //放行校验验证码
    }
}
