package com.example.yellowaution.controller;

import com.example.yellowaution.dto.EmailRequest;
import com.example.yellowaution.dto.PasswordRecoveryRequest;
import com.example.yellowaution.dto.ResetPasswordRequest;
import com.example.yellowaution.dto.VerifyCodeRequest;
import com.example.yellowaution.service.AccountRecoveryService;
import com.example.yellowaution.service.PasswordResetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/recover")
public class RecoveryRestController {

    private final AccountRecoveryService accountRecoveryService;
    private final PasswordResetService passwordResetService;

    public RecoveryRestController(
            AccountRecoveryService accountRecoveryService,
            PasswordResetService passwordResetService
    ) {
        this.accountRecoveryService = accountRecoveryService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/username")
    public ResponseEntity<Map<String, String>> recoverUsername(
            @Valid @RequestBody EmailRequest request
    ) {
        accountRecoveryService.sendUsernameByEmail(request.getEmail());
        return ResponseEntity.ok(Map.of("message", "이메일로 아이디를 발송했습니다."));
    }
    /** 1) 인증번호 요청 */
    @PostMapping("/password/token")
    public ResponseEntity<Map<String, String>> sendCode(
            @Valid @RequestBody PasswordRecoveryRequest req) {
        passwordResetService.sendVerificationCode(req.getUsername(), req.getEmail());
        return ResponseEntity.ok(Map.of("message", "인증번호를 이메일로 발송했습니다."));
    }

    /** 2) 인증번호 검증 */
    @PostMapping("/password/verify")
    public ResponseEntity<Map<String,String>> verifyCode(
            @Valid @RequestBody VerifyCodeRequest req) {
        passwordResetService.verifyCode(req.getUsername(), req.getEmail(), req.getCode());
        return ResponseEntity.ok(Map.of("message", "인증번호가 확인되었습니다."));
    }

    /** 3) 새 비밀번호 변경 */
    @PostMapping("/password")
    public ResponseEntity<Map<String,String>> resetPwd(
            @Valid @RequestBody ResetPasswordRequest req) {
        passwordResetService.resetPassword(req.getToken(), req.getNewPassword());
        return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
    }

}
