package com.example.yellowaution.controller.page;

import com.example.yellowaution.service.AccountRecoveryService;
import com.example.yellowaution.service.PasswordResetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recover")
public class RecoveryController {

    private final AccountRecoveryService accountRecoveryService;
    private final PasswordResetService passwordResetService;

    public RecoveryController(
            AccountRecoveryService accountRecoveryService,
            PasswordResetService passwordResetService
    ) {
        this.accountRecoveryService = accountRecoveryService;
        this.passwordResetService = passwordResetService;
    }

    // — 아이디 찾기 페이지 (뷰만)
    @GetMapping("/username")
    public String showUsernameForm() {
        return "recover/username";
    }

    // — 비밀번호 찾기 페이지 (뷰만, AJAX 로직)
    @GetMapping("/password")
    public String showPasswordForm() {
        return "recover/password";
    }

}
