package com.example.yellowaution.handler;

import com.example.yellowaution.domain.User;
import com.example.yellowaution.repository.UserRepository;
import com.example.yellowaution.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * CustomLoginSuccessHandler 클래스는 Spring Security 로그인 성공 후
 * 추가 처리를 담당하는 핸들러입니다.
 *
 * <ul>
 *   <li>로그인한 User 엔티티를 HttpSession에 저장</li>
 *   <li>사용자 유형(userType)에 따라 서로 다른 대시보드 페이지로 리다이렉트</li>
 * </ul>
 */
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    /** 사용자 조회를 위한 JPA 레포지토리 */
    private final UserRepository userRepository;

    /**
     * 생성자 주입(Constructor Injection)을 통해 UserRepository를 할당합니다.
     *
     * @param userRepository 사용자 정보 조회/저장용 레포지토리
     */
    public CustomLoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 로그인 성공 시 호출되는 메서드입니다.
     *
     * @param request        HTTP 요청 객체
     * @param response       HTTP 응답 객체
     * @param authentication 인증 객체(인증된 사용자 정보 포함)
     * @throws IOException 리다이렉트 처리 중 예외 발생 시
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // Authentication에서 UserPrincipal 객체를 꺼내 실제 User 엔티티를 가져옴
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        User user = principal.getUser();

        // 세션에 로그인 사용자 정보(loginUser) 저장
        // 이후 컨트롤러나 뷰에서 세션을 통해 user 정보 활용 가능
        request.getSession().setAttribute("loginUser", user);

        // 사용자 유형(userType)에 따라 리다이렉트할 URL을 결정
        String redirectUrl = switch (user.getUserType()) {
            case "EMPLOYER" -> "/dashboard/com_dashboard";      // 기업 사용자 대시보드
            case "FREELANCER" -> "/dashboard/free_dashboard";  // 프리랜서 대시보드
            case "ADMIN" -> "/dashboard/admin_dashboard";      // 관리자 대시보드
            default -> "/";                                    // 그 외 기본 페이지
        };

        // 결정된 URL로 리다이렉트
        response.sendRedirect(redirectUrl);
    }
}
