package com.example.yellowaution.repository;

import com.example.yellowaution.domain.PasswordResetToken;
import com.example.yellowaution.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * PasswordResetTokenRepository 인터페이스는 JPA를 통해
 * PasswordResetToken 엔티티에 대한 CRUD 및 토큰 검증/정리 기능을 제공합니다.
 *
 * <p>주요 역할:
 * <ul>
 *   <li>토큰 값(token)으로 PasswordResetToken 조회</li>
 *   <li>특정 사용자(User)에 연관된 모든 토큰 일괄 삭제</li>
 * </ul>
 * </p>
 *
 * <p>JpaRepository&lt;PasswordResetToken, Long&gt;을 상속하여
 * 기본적인 save, findById, findAll, delete 등 메서드를 자동으로 사용할 수 있습니다.</p>
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    /**
     * 주어진 토큰 문자열을 키로 PasswordResetToken 엔티티를 조회합니다.
     *
     * <p>사용 예:
     * <pre>
     * Optional<PasswordResetToken> opt = tokenRepo.findByToken(tokenString);
     * if (opt.isEmpty()) {
     *     throw new TokenNotFoundException("유효하지 않은 토큰입니다.");
     * }
     * PasswordResetToken prt = opt.get();
     * </pre>
     * </p>
     *
     * @param token 이메일로 발송된 검증 토큰(UUID 문자열)
     * @return Optional&lt;PasswordResetToken&gt; — 토큰이 존재하면 해당 엔티티, 없으면 Optional.empty()
     */
    Optional<PasswordResetToken> findByToken(String token);

    /**
     * 특정 사용자(User)에 연관된 모든 PasswordResetToken 레코드를 삭제합니다.
     *
     * <p>동작:
     * <ol>
     *   <li>user 객체로 식별된 사용자의 모든 토큰을 찾고</li>
     *   <li>deleteAllByUser 메서드 호출 시 내부적으로 DELETE 쿼리를 실행</li>
     * </ol>
     * </p>
     *
     * <p>서비스 레이어에서 새로운 토큰을 생성하기 전에
     * 기존의 모든 토큰을 먼저 삭제하여, 하나의 사용자당 활성 토큰이 하나만 남도록 할 때 사용합니다.</p>
     *
     * @param user 토큰을 삭제할 대상 사용자(User 엔티티)
     */
    void deleteAllByUser(User user);
}
