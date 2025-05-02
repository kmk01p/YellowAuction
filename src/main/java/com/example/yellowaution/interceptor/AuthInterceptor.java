package com.example.yellowaution.interceptor;

import com.example.yellowaution.domain.User;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse res,
                             Object handler) throws Exception {
        HttpSession session = req.getSession(false);
        if (session==null || session.getAttribute("loginUser")==null) {
            res.sendRedirect("/login");
            return false;
        }
        User u = (User)session.getAttribute("loginUser");
        String uri = req.getRequestURI();

        // ADMIN은 모든 페이지 접근 허용
        if ("ADMIN".equals(u.getRole())) {
            return true;
        }
        // 구인(EMPLOYER) 전용
        if (uri.startsWith("/com") && !"EMPLOYER".equals(u.getUserType())) {
            res.sendRedirect("/access-denied");
            return false;
        }
        // 구직(FREELANCER) 전용
        if (uri.startsWith("/free") && !"FREELANCER".equals(u.getUserType())) {
            res.sendRedirect("/access-denied");
            return false;
        }
        return true;
    }
}
