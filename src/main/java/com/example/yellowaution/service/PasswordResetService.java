package com.example.yellowaution.service;



public interface PasswordResetService {
    /**
     * 이메일을 받아 비밀번호 재설정용 토큰을 생성한 뒤,
     * 해당 토큰을 포함한 재설정 링크를 이메일로 발송합니다.
     */
    void createAndSendResetToken(String email);

    /**
     * 전달된 토큰을 검증하고, 새 비밀번호로 업데이트합니다.
     */
    void resetPassword(String token, String newPassword);
}
