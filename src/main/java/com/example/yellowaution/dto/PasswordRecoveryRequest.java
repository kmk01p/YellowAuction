package com.example.yellowaution.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * PasswordRecoveryRequest 클래스는 비밀번호 복구를 위해
 * 사용자 이름과 이메일을 입력받는 요청 데이터를 담는 DTO입니다.
 *
 * <p>유효성 검증:
 * <ul>
 *   <li>username: 공백이 아니어야 합니다.</li>
 *   <li>email: 유효한 이메일 형식이어야 하며, 공백이 아니어야 합니다.</li>
 * </ul>
 * </p>
 *
 * <p>AccountRecoveryController 또는 RecoveryRestController에서
 * 인증번호 발송 단계의 입력 파라미터로 사용됩니다.</p>
 */
@Getter
@Setter
public class PasswordRecoveryRequest {

    /**
     * 비밀번호 복구 대상 사용자의 고유 아이디(username).
     * - 공백 문자열이 아닌 값을 입력해야 합니다.
     */
    @NotBlank(message = "아이디는 필수 입력입니다.")
    private String username;

    /**
     * 비밀번호 복구 시 사용할 이메일 주소(email).
     * - 유효한 이메일 형식이어야 합니다.
     * - 공백 문자열이 아닌 값을 입력해야 합니다.
     */
    @Email(message = "유효한 이메일 주소여야 합니다.")
    @NotBlank(message = "이메일은 필수 입력입니다.")
    private String email;
}
