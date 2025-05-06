package com.example.yellowaution.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Bid 엔티티는 하나의 입찰 내역을 나타냅니다.
 *
 * <p>주요 역할:
 * <ul>
 *   <li>어떤 게시물(Board)에 대해 누가(User) 얼마(amount)를 입찰했는지를 저장</li>
 *   <li>입찰 생성 시각(createdAt)을 자동으로 기록</li>
 * </ul>
 * </p>
 *
 * <p>JPA Auditing 기능을 사용하여 엔티티 생성 시점을 자동으로 관리합니다.</p>
 */
@Setter
@Getter
@Entity                                       // JPA Entity로 인식되어 DB 테이블과 매핑
@EntityListeners(AuditingEntityListener.class) // 생성 일시 자동 저장을 위한 Auditing 리스너 등록
public class Bid {

    /**
     * 입찰의 고유 식별자(ID)입니다.
     *
     * - @Id: 기본 키(primary key)로 사용
     * - @GeneratedValue: DB에 insert할 때 ID를 자동 생성
     *   - strategy = GenerationType.IDENTITY: MySQL 등의 DB에서 AUTO_INCREMENT 컬럼 사용
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 이 입찰이 속한 게시물(Board)과 다대일(Many-to-One) 관계를 맺습니다.
     *
     * - 여러 개의 Bid가 하나의 Board에 연결될 수 있음
     * - 기본 FetchType은 EAGER이나, 성능 최적화를 위해 LAZY로 설정하는 것을 고려할 수 있습니다.
     */
    @JsonIgnore
    @ManyToOne
    private Board board;

    /**
     * 이 입찰을 수행한 사용자(User)와 다대일(Many-to-One) 관계를 맺습니다.
     *
     * - 여러 개의 Bid가 하나의 User에 연결될 수 있음
     * - 입찰자 정보 조회 시 User 엔티티 전체를 로딩하므로, 필요한 경우 FetchType 조정 가능
     */
    @ManyToOne
    private User user;

    /**
     * 입찰 금액(amount)입니다. 단위는 원(₩).
     *
     * - 최소 입찰 단위 및 최대 입찰가 제한은 서비스 레이어(BoardService 또는 BidService)에서 검증하도록 구현
     * - 예: amount >= AuctionConstants.MINIMUM_STARTING_BID
     */
    private Long amount;

    /**
     * 입찰 생성 시각(createdAt)을 나타냅니다.
     *
     * - @CreatedDate: 엔티티 최초 저장 시점에 자동으로 값이 주입
     * - LocalDateTime: 날짜 및 시각(년-월-일 시:분:초)을 저장
     * - 타임존은 애플리케이션 설정(예: application.yaml)에서 serverTimezone=Asia/Seoul로 관리
     */
    @CreatedDate
    private LocalDateTime createdAt;
}
