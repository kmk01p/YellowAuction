package com.example.yellowaution.controller.page;

import com.example.yellowaution.service.AccountRecoveryService;
import com.example.yellowaution.service.PasswordResetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * RecoveryController 클래스는 계정 복구(아이디/비밀번호 찾기) 관련 페이지 요청을 처리합니다.
 *
 * <ul>
 *   <li>GET /recover/username : 아이디 찾기 폼 페이지 렌더링</li>
 *   <li>GET /recover/password : 비밀번호 찾기 폼 페이지 렌더링 (AJAX 처리 로직과 연동)</li>
 * </ul>
 *
 * 계정 복구 로직은 AccountRecoveryService와 PasswordResetService를 통해
 * 실제 비즈니스 로직(이메일 전송, 토큰 검증 등)을 처리합니다.
 */
@Controller
@RequestMapping("/recover")
public class RecoveryController {

    /**
     * AccountRecoveryService는 아이디 찾기(이메일 또는 기타 인증 수단을 통한 사용자 확인) 로직을 담당합니다.
     */
    private final AccountRecoveryService accountRecoveryService;

    /**
     * PasswordResetService는 비밀번호 초기화(이메일 링크 발송, 토큰 인증 후 비밀번호 변경) 로직을 담당합니다.
     */
    private final PasswordResetService passwordResetService;

    /**
     * 생성자 주입(Constructor Injection)을 통해 서비스 의존성을 설정합니다.
     *
     * @param accountRecoveryService 아이디 찾기 서비스
     * @param passwordResetService   비밀번호 재설정 서비스
     */
    public RecoveryController(
            AccountRecoveryService accountRecoveryService,
            PasswordResetService passwordResetService
    ) {
        this.accountRecoveryService = accountRecoveryService;
        this.passwordResetService = passwordResetService;
    }

    /**
     * 아이디 찾기 폼 페이지를 보여주는 핸들러 메서드입니다.
     *
     * <p>요청 URL: GET /recover/username
     * <br>반환 뷰: resources/templates/recover/username.html</p>
     *
     * @return 아이디 찾기 폼 뷰 이름("recover/username")
     */
    @GetMapping("/username")
    public String showUsernameForm() {
        // POST 핸들러 메서드를 추가하고 해당 서비스 메서드를 호출하세요.
        return "recover/username";
    }

    /**
     * 비밀번호 찾기 폼 페이지를 보여주는 핸들러 메서드입니다.
     *
     * <p>요청 URL: GET /recover/password
     * <br>반환 뷰: resources/templates/recover/password.html</p>
     *
     * <p>AJAX 로직:
     * - 클라이언트 측에서 비밀번호 찾기 폼 데이터를 제출하면,
     *   PasswordResetService를 호출하여 이메일 발송 또는 토큰 생성 등을 수행합니다.</p>
     *
     * @return 비밀번호 찾기 폼 뷰 이름("recover/password")
     */
    @GetMapping("/password")
    public String showPasswordForm() {
        // PasswordResetService의 메서드를 호출하여 비밀번호 초기화 이메일 발송 로직을 연결하세요.
        return "recover/password";
    }

}
