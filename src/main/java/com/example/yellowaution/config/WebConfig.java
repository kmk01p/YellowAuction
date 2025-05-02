package com.example.yellowaution.config;

import com.example.yellowaution.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    public WebConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login", "/login/**",           // 페이지 로그인
                        "/register", "/register/**",     // 페이지 회원가입
                        "/api/auth/**",                  // REST 로그인·회원가입·로그아웃
                        "/css/**", "/js/**", "/images/**",
                        "/favicon.ico",                  // 파비콘
                        "/error"                         // 스프링 기본 에러 디스패치
                );
    }
}
