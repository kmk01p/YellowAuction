package com.example.yellowaution.service;

public interface AccountRecoveryService {
    /**
     * 이메일로 가입된 아이디(username)를 조회해 발송
     */
    void sendUsernameByEmail(String email);

}
