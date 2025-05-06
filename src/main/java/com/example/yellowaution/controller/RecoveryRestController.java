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

/**
 * RecoveryRestController 클래스는 REST API 방식으로
 * 계정 복구(아이디 찾기, 비밀번호 재설정) 기능을 제공합니다.
 *
 * <p>주요 흐름:
 * <ol>
 *   <li>아이디 찾기: 이메일을 입력받아 사용자 아이디를 이메일로 발송</li>
 *   <li>비밀번호 복구(3단계)
 *     <ol>
 *       <li>인증번호 요청: 아이디와 이메일을 입력받아 인증번호 발송</li>
 *       <li>인증번호 검증: 사용자가 입력한 인증번호 유효성 확인</li>
 *       <li>새 비밀번호 설정: 검증된 토큰으로 새 비밀번호 저장</li>
 *     </ol>
 *   </li>
 * </ol>
 * </p>
 */
@RestController
@RequestMapping("/api/recover")
public class RecoveryRestController {

    /**
     * 아이디 찾기 관련 비즈니스 로직을 처리하는 서비스.
     * - sendUsernameByEmail(email): 이메일로 아이디 발송
     */
    private final AccountRecoveryService accountRecoveryService;

    /**
     * 비밀번호 재설정 관련 비즈니스 로직을 처리하는 서비스.
     * - sendVerificationCode(username, email): 인증번호 발송
     * - verifyCode(username, email, code): 인증번호 검증
     * - resetPassword(token, newPassword): 새 비밀번호 저장
     */
    private final PasswordResetService passwordResetService;

    /**
     * 생성자 주입(Constructor Injection)을 통해 서비스 의존성을 설정합니다.
     *
     * @param accountRecoveryService 아이디 찾기 서비스
     * @param passwordResetService   비밀번호 재설정 서비스
     */
    public RecoveryRestController(
            AccountRecoveryService accountRecoveryService,
            PasswordResetService passwordResetService
    ) {
        this.accountRecoveryService = accountRecoveryService;
        this.passwordResetService = passwordResetService;
    }

    /**
     * 1) 아이디 찾기: 사용자가 입력한 이메일로 아이디를 발송합니다.
     *
     * <p>요청:
     * POST /api/recover/username
     * <br>요청 바디: { "email": "user@example.com" }</p>
     *
     * @param request 이메일 정보가 담긴 DTO (EmailRequest)
     * @return HTTP 200 OK와 함께 메시지 맵 반환
     */
    @PostMapping("/username")
    public ResponseEntity<Map<String, String>> recoverUsername(
            @Valid @RequestBody EmailRequest request
    ) {
        accountRecoveryService.sendUsernameByEmail(request.getEmail());
        return ResponseEntity.ok(Map.of("message", "이메일로 아이디를 발송했습니다."));
    }

    /**
     * 2) 비밀번호 복구 ― 인증번호 요청: 사용자가 입력한 아이디와 이메일로
     * 인증번호를 생성하여 이메일로 발송합니다.
     *
     * <p>요청:
     * POST /api/recover/password/token
     * <br>요청 바디: { "username": "user1", "email": "user@example.com" }</p>
     *
     * @param req 아이디와 이메일 정보를 담은 DTO (PasswordRecoveryRequest)
     * @return HTTP 200 OK와 함께 메시지 맵 반환
     */
    @PostMapping("/password/token")
    public ResponseEntity<Map<String, String>> sendCode(
            @Valid @RequestBody PasswordRecoveryRequest req) {
        passwordResetService.sendVerificationCode(req.getUsername(), req.getEmail());
        return ResponseEntity.ok(Map.of("message", "인증번호를 이메일로 발송했습니다."));
    }

    /**
     * 3) 비밀번호 복구 ― 인증번호 검증: 사용자가 받은 인증번호가 유효한지 확인합니다.
     *
     * <p>요청:
     * POST /api/recover/password/verify
     * <br>요청 바디: { "username": "user1", "email": "user@example.com", "code": "123456" }</p>
     *
     * @param req 아이디, 이메일, 인증번호 정보를 담은 DTO (VerifyCodeRequest)
     * @return HTTP 200 OK와 함께 메시지 맵 반환
     */
    @PostMapping("/password/verify")
    public ResponseEntity<Map<String, String>> verifyCode(
            @Valid @RequestBody VerifyCodeRequest req) {
        passwordResetService.verifyCode(req.getUsername(), req.getEmail(), req.getCode());
        return ResponseEntity.ok(Map.of("message", "인증번호가 확인되었습니다."));
    }

    /**
     * 4) 비밀번호 복구 ― 새 비밀번호 변경: 검증된 토큰을 사용하여
     * 사용자의 비밀번호를 새 비밀번호로 업데이트합니다.
     *
     * <p>요청:
     * POST /api/recover/password
     * <br>요청 바디: { "token": "abcdef...", "newPassword": "newPass123!" }</p>
     *
     * @param req 토큰과 새 비밀번호 정보를 담은 DTO (ResetPasswordRequest)
     * @return HTTP 200 OK와 함께 메시지 맵 반환
     */
    @PostMapping("/password")
    public ResponseEntity<Map<String, String>> resetPwd(
            @Valid @RequestBody ResetPasswordRequest req) {
        passwordResetService.resetPassword(req.getToken(), req.getNewPassword());
        return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
    }

}
