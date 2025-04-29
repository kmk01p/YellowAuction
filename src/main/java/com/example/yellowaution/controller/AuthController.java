package com.example.yellowaution.controller;

import com.example.yellowaution.domain.User;
import com.example.yellowaution.dto.LoginRequestDto;
import com.example.yellowaution.dto.SignupRequestDto;
import com.example.yellowaution.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginRequestDto", new LoginRequestDto());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDto dto, Model model) {
        User user = authService.login(dto);
        if (user != null) {
            model.addAttribute("user", user);
            return "redirect:/projects";
        } else {
            model.addAttribute("error", "로그인 실패");
            return "login";
        }
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupRequestDto", new SignupRequestDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute SignupRequestDto dto) {
        authService.signup(dto);
        return "redirect:/login";
    }
}
