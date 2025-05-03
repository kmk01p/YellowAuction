package com.example.yellowaution.controller.page;

import com.example.yellowaution.domain.User;
import com.example.yellowaution.dto.UserRegisterDto;
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
    public String register(@ModelAttribute UserRegisterDto dto) {
        userService.register(dto);
        return "redirect:/login";
    }


    // 로그인 폼
    @GetMapping("/login")
    public String loginForm(@RequestParam(name="error", required=false) String error,
                            Model model) {
        if (error!=null) model.addAttribute("loginError","로그인 실패");
        return "login";
    }


    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
