package com.example.yellowaution.controller;



import com.example.yellowaution.dto.EmailRequest;
import com.example.yellowaution.dto.ResetPasswordRequest;
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

    /**
     * 1) 아이디(Username) 찾기
     * POST /api/recover/username
     */
    @PostMapping("/username")
    public ResponseEntity<Map<String, String>> recoverUsername(
            @Valid @RequestBody EmailRequest request
    ) {
        accountRecoveryService.sendUsernameByEmail(request.getEmail());
        return ResponseEntity.ok(Map.of("message", "이메일로 아이디를 발송했습니다."));
    }

    /**
     * 2) 비밀번호 재설정용 토큰 발송
     * POST /api/recover/password/token
     */
    @PostMapping("/password/token")
    public ResponseEntity<Map<String, String>> sendResetToken(
            @Valid @RequestBody EmailRequest request
    ) {
        passwordResetService.createAndSendResetToken(request.getEmail());
        return ResponseEntity.ok(Map.of("message", "이메일로 비밀번호 재설정 링크를 발송했습니다."));
    }

    /**
     * 3) 토큰으로 새 비밀번호 설정
     * POST /api/recover/password
     */
    @PostMapping("/password")
    public ResponseEntity<Map<String, String>> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
    ) {
        passwordResetService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
    }

}
