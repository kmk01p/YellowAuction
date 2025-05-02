package com.example.yellowaution.interceptor;

import com.example.yellowaution.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    /**
     * 1) 모든 요청에 대해 세션(loginUser) 유무를 검사합니다.
     * 2) URI가 /admin... 으로 시작하면, 세션이 있어도 role 검사까지 수행합니다.
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        // 1) 로그인 여부 검사
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect("/login");
            return false;
        }

        // 2) ADMIN 전용 경로 권한 검사
        User user = (User) session.getAttribute("loginUser");
        String uri = request.getRequestURI();
        if (uri.startsWith("/admin") && !"ADMIN".equals(user.getRole())) {
            response.sendRedirect("/access-denied");
            return false;
        }

        // 그 외 요청은 모두 허용
        return true;
    }
}
