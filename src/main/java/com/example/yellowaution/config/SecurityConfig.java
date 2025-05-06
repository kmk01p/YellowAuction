package com.example.yellowaution.config;

import com.example.yellowaution.handler.CustomLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 관련 설정을 담당하는 클래스입니다.
 *
 * <ul>
 *   <li>@Configuration: 스프링 설정 클래스로 등록하여 빈 등록을 허용합니다.</li>
 *   <li>@EnableMethodSecurity: @PreAuthorize, @PostAuthorize 등의 메서드 보안 어노테이션을 활성화합니다.</li>
 * </ul>
 */
@EnableMethodSecurity  // @PreAuthorize 같은 메서드 레벨 권한 검사를 사용하려면 반드시 추가
@Configuration
public class SecurityConfig {

    /**
     * 로그인 성공 시 추가 로직(대시보드로 리다이렉트 등)을 처리하는
     * CustomLoginSuccessHandler를 주입받습니다.
     */
    private final CustomLoginSuccessHandler customLoginSuccessHandler;

    public SecurityConfig(CustomLoginSuccessHandler customLoginSuccessHandler) {
        this.customLoginSuccessHandler = customLoginSuccessHandler;
    }

    /**
     * 비밀번호를 안전하게 저장하기 위해 BCrypt 해시 함수를 사용하는
     * BCryptPasswordEncoder 빈을 등록합니다.
     *
     * <p>Spring Security 내부에서 사용자 비밀번호 비교 시 이 빈을 사용하여
     * 평문 비밀번호를 해시하여 저장된 해시값과 비교하게 됩니다.</p>
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * HTTP 요청에 대한 보안 필터 체인을 설정합니다.
     *
     * <ol>
     *   <li>CSRF 보호 비활성화 (API 사용 시 토큰 기반 방식을 별도 적용한다면 허용)</li>
     *   <li>URL별 접근 권한 지정
     *     <ul>
     *       <li>로그인, 회원가입, 복구 API, 정적 리소스(css/js/images)는 모두 허용</li>
     *       <li>관리자 대시보드 접근은 ADMIN 권한 보유자만</li>
     *       <li>기업 사용자 대시보드 및 기업 등록 페이지는 EMPLOYER 권한 보유자만</li>
     *       <li>프리랜서 대시보드 및 프리랜서 등록 페이지는 FREELANCER 권한 보유자만</li>
     *       <li>그 외 모든 요청은 인증된 사용자만 접근 가능</li>
     *     </ul>
     *   </li>
     *   <li>폼 로그인 설정
     *     <ul>
     *       <li>커스텀 로그인 페이지 URL: /login</li>
     *       <li>로그인 처리 URL: /login (POST)</li>
     *       <li>로그인 성공 시 CustomLoginSuccessHandler가 후속 처리</li>
     *     </ul>
     *   </li>
     *   <li>로그아웃 설정
     *     <ul>
     *       <li>로그아웃 성공 후 이동할 URL: /login</li>
     *       <li>세션 무효화 및 JSESSIONID 쿠키 삭제</li>
     *     </ul>
     *   </li>
     * </ol>
     *
     * @param http HttpSecurity 빌더
     * @return 구성된 SecurityFilterChain 빈
     * @throws Exception 설정 중 예외 발생 시
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // REST API 사용 시에는 CSRF 토큰 대신 별도 인증 방식을 쓰므로 비활성화
                .csrf().disable()

                // URL별 권한 정책 정의
                .authorizeHttpRequests(auth -> auth
                        // 인증 없이 접근 허용할 URL 패턴
                        .requestMatchers(
                                "/login",
                                "/register",
                                "/api/auth/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/recover/**",
                                "/api/recover/**"
                        ).permitAll()

                        // ADMIN 권한을 가진 사용자만 접근
                        .requestMatchers("/dashboard/admin_dashboard").hasAuthority("ADMIN")

                        // EMPLOYER 권한을 가진 사용자만 접근
                        .requestMatchers("/dashboard/com_dashboard", "/create/com_create")
                        .hasAuthority("EMPLOYER")

                        // FREELANCER 권한을 가진 사용자만 접근
                        .requestMatchers("/dashboard/free_dashboard", "/create/free_create")
                        .hasAuthority("FREELANCER")

                        // 그 외 모든 요청은 인증된 사용자만
                        .anyRequest().authenticated()
                )

                // 폼 로그인 설정
                .formLogin(form -> form
                        .loginPage("/login")                          // 커스텀 로그인 페이지 경로
                        .loginProcessingUrl("/login")                 // 로그인 폼 submit 처리 URL
                        .successHandler(customLoginSuccessHandler)    // 로그인 성공 후 처리 핸들러
                        .permitAll()                                  // 로그인 페이지 자체는 모두 접근 허용
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")    // 로그아웃 성공 시 이동할 URL
                        .invalidateHttpSession(true)   // 세션 무효화
                        .deleteCookies("JSESSIONID")   // JSESSIONID 쿠키 삭제
                        .permitAll()
                );

        // 설정 완료 후 빌드하여 반환
        return http.build();
    }
}
