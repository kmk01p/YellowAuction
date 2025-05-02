package com.example.yellowaution.controller.page;

import com.example.yellowaution.domain.User;
import com.example.yellowaution.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 폼
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String userType) {
        userService.register(username, password, userType);
        return "redirect:/login";
    }

    // 로그인 폼
    @GetMapping("/login")
    public String loginForm(@RequestParam(name="error", required=false) String error,
                            Model model) {
        if (error!=null) model.addAttribute("loginError","로그인 실패");
        return "login";
    }

    // 로그인 처리 → 역할·유형별 대시보드로 리다이렉트
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
        User user = userService.login(username, password);
        session.setAttribute("loginUser", user);
        if ("ADMIN".equals(user.getRole())) {
            return "redirect:/admin/dashboard";
        } else if ("EMPLOYER".equals(user.getUserType())) {
            return "redirect:/com/dashboard";
        } else {
            return "redirect:/free/dashboard";
        }
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
