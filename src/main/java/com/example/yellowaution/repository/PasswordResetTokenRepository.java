package com.example.yellowaution.repository;


import com.example.yellowaution.domain.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    /**
     * 토큰 문자열로 조회
     */
    Optional<PasswordResetToken> findByToken(String token);
}
