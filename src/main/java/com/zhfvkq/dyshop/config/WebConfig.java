package com.zhfvkq.dyshop.config;

import com.zhfvkq.dyshop.config.Interceptor.LogInterceptor;
import com.zhfvkq.dyshop.config.Interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LogInterceptor())
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/js/**", "/img/**", "/css/**","/*.ico","/"); // interceptor 제외 경로

        // 사용장 인증 = > 스프링 시큐리티 사용
//        registry.addInterceptor(new LoginCheckInterceptor())
//                .order(2)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/js/**","/css/**","/img/**","/*.ico","/error", "/", "/member/signup","/member/login","/logout");
    }
}
