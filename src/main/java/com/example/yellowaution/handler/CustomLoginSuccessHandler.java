package com.example.yellowaution.handler;

import com.example.yellowaution.domain.User;
import com.example.yellowaution.repository.UserRepository;
import com.example.yellowaution.security.UserPrincipal;
import jakarta.servlet.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public CustomLoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        User user = principal.getUser();

        request.getSession().setAttribute("loginUser", user);

        String redirectUrl = switch (user.getUserType()) {
            case "EMPLOYER" -> "/dashboard/com_dashboard";
            case "FREELANCER" -> "/dashboard/free_dashboard";
            case "ADMIN" -> "/dashboard/admin_dashboard";
            default -> "/";
        };

        response.sendRedirect(redirectUrl);
    }
}
