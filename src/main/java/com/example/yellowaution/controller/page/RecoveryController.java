// src/main/java/com/example/yellowaution/controller/page/RecoveryController.java
package com.example.yellowaution.controller.page;

import com.example.yellowaution.service.AccountRecoveryService;
import com.example.yellowaution.service.PasswordResetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/recover")
public class RecoveryController {

    private final AccountRecoveryService accountRecoveryService;
    private final PasswordResetService passwordResetService;

    public RecoveryController(AccountRecoveryService accountRecoveryService,
                              PasswordResetService passwordResetService) {
        this.accountRecoveryService = accountRecoveryService;
        this.passwordResetService = passwordResetService;
    }

    // — 아이디 찾기 페이지
    @GetMapping("/username")
    public String showUsernameForm(@ModelAttribute("message") String message) {
        return "recover/username";
    }
    @PostMapping("/username")
    public String handleUsername(@RequestParam String email,
                                 RedirectAttributes ra) {
        accountRecoveryService.sendUsernameByEmail(email);
        ra.addFlashAttribute("message", "입력하신 이메일로 아이디를 발송했습니다.");
        return "redirect:/recover/username";
    }

    // — 비밀번호 찾기 / 재설정 페이지
    @GetMapping("/password")
    public String showPasswordForm(@RequestParam(required = false) String token,
                                   @ModelAttribute("message") String message,
                                   Model model) {
        // token이 있을 때는 새 비밀번호 폼, 없으면 이메일 입력 폼
        model.addAttribute("token", token);
        return "recover/password";
    }
    @PostMapping("/password")
    public String handlePasswordRequest(@RequestParam String email,
                                        RedirectAttributes ra) {
        passwordResetService.createAndSendResetToken(email);
        ra.addFlashAttribute("message", "재설정 링크를 이메일로 발송했습니다.");
        return "redirect:/recover/password";
    }
}
