package com.example.yellowaution.config;

import com.example.yellowaution.handler.CustomLoginSuccessHandler;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity // ★ 이거 추가해야 @PreAuthorize 동작함
@Configuration
public class SecurityConfig {

    private final CustomLoginSuccessHandler customLoginSuccessHandler;

    public SecurityConfig(CustomLoginSuccessHandler customLoginSuccessHandler) {
        this.customLoginSuccessHandler = customLoginSuccessHandler;
    }

    // 비밀번호 암호화 Bean 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 보안 필터 체인 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/api/auth/**", "/css/**", "/js/**", "/images/**","/recover/**","/api/recover/**").permitAll()
                        .requestMatchers("/dashboard/admin_dashboard").hasAuthority("ADMIN")
                        .requestMatchers("/dashboard/com_dashboard", "/create/com_create").hasAuthority("EMPLOYER")
                        .requestMatchers("/dashboard/free_dashboard", "/create/free_create").hasAuthority("FREELANCER")
                        .anyRequest().authenticated()
                )


                .formLogin(form -> form
                        .loginPage("/login")                         // 로그인 폼 페이지
                        .loginProcessingUrl("/login")                // 로그인 POST 요청 경로
                        .successHandler(customLoginSuccessHandler)   // 로그인 성공 핸들러
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}
