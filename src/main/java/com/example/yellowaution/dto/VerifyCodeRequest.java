package com.example.yellowaution.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * VerifyCodeRequest 클래스는 비밀번호 복구 과정 중 인증번호 검증 단계에서
 * 클라이언트가 전달하는 요청 데이터를 담는 DTO입니다.
 *
 * <p>유효성 검증:
 * <ul>
 *   <li>@NotBlank: 필수 입력 항목으로, null 또는 빈 문자열을 허용하지 않음</li>
 *   <li>@Email: RFC 표준에 맞는 이메일 형식인지 확인</li>
 * </ul>
 * </p>
 *
 * <p>RecoveryRestController.verifyCode() 핸들러에서 사용되며,
 * 사용자가 입력한 인증번호가 서버에 저장된 코드와 일치하는지 검사할 때 활용됩니다.</p>
 */
@Getter
@Setter
public class VerifyCodeRequest {

    /**
     * 비밀번호 복구 대상 사용자의 아이디(username)
     * - @NotBlank: null 또는 빈 문자열이 아닌 값을 입력해야 함
     */
    @NotBlank(message = "아이디는 필수 입력입니다.")
    private String username;

    /**
     * 비밀번호 복구 시 사용된 이메일 주소(email)
     * - @Email: 올바른 이메일 형식인지 검증
     * - @NotBlank: null 또는 빈 문자열이 아닌 값을 입력해야 함
     */
    @Email(message = "유효한 이메일 주소여야 합니다.")
    @NotBlank(message = "이메일은 필수 입력입니다.")
    private String email;

    /**
     * 사용자가 이메일로 전달받은 인증번호(code)
     * - @NotBlank: null 또는 빈 문자열이 아닌 값을 입력해야 함
     */
    @NotBlank(message = "인증번호는 필수 입력입니다.")
    private String code;
}
