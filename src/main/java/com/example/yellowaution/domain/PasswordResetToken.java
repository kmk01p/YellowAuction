package com.example.yellowaution.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * PasswordResetToken 엔티티는 비밀번호 재설정을 위한 토큰 정보를 저장합니다.
 *
 * <p>주요 역할:
 * <ul>
 *   <li>UUID로 생성된 토큰(token)과 만료 시각(expiry)을 저장</li>
 *   <li>토큰 발급 대상인 User 엔티티와 1:1 관계 설정</li>
 * </ul>
 * </p>
 *
 * <p>비밀번호 재설정 요청 시, 해당 토큰이 유효한지(expiry 검사) 확인한 뒤
 * 사용자의 비밀번호를 재설정하도록 서비스 레이어에서 활용됩니다.</p>
 */
@Entity  // JPA Entity로 매핑되어 데이터베이스 테이블과 연동
@Getter
@Setter
public class PasswordResetToken {

    /**
     * 토큰 엔티티의 고유 식별자(ID)
     * - @Id: 기본 키(primary key) 설정
     * - @GeneratedValue: 기본 생성 전략(AUTO)을 사용해 값 자동 생성
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 실제 비밀번호 재설정에 사용되는 토큰 문자열
     * - UUID.randomUUID().toString() 형태로 생성
     * - 토큰값을 클라이언트에 이메일로 전송 후 재설정 검증에 사용
     */
    private String token;

    /**
     * 토큰 만료 시각
     * - token 생성 시점에 +유효기간(예: 1시간) 만큼 더한 LocalDateTime
     * - 서비스 레이어에서 현재 시간과 비교하여 유효 여부 검증
     */
    private LocalDateTime expiry;

    /**
     * 이 토큰이 연관된 사용자(User)와의 1:1 관계 매핑
     * - @OneToOne: User 엔티티와 일대일 관계
     * - @JoinColumn(name = "user_id"): 외래 키 컬럼명을 지정
     * - 토큰 삭제 시, 사용자 정보는 그대로 유지되며
     *   필요 시 cascade 속성 추가 검토 가능
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
