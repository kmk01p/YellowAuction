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

    // 홈 페이지 ("/" 와 "/index" 둘 다 이 메서드로 처리)
    @GetMapping({"/", "/index"})
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser", user);
        return "index";  // src/main/resources/templates/index.html 을 렌더링
    }

    // 이하 기존 코드...
    @GetMapping("/register")
    public String registerForm() { return "register"; }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam(defaultValue = "USER") String role) {
        userService.register(username, password, role);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(name="error", required=false) String error,
                            Model model) {
        if (error != null) {
            model.addAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
        User user = userService.login(username, password);
        session.setAttribute("loginUser", user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
