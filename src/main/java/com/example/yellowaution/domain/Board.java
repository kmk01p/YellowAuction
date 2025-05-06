package com.example.yellowaution.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Board 엔티티는 프리랜서 · 기업 프로젝트(게시물) 정보를 저장합니다.
 *
 * <p>주요 역할:
 * <ul>
 *   <li>프로젝트 제목(title), 마감 기한(dueDate), 설명(description) 등 핵심 정보 보관</li>
 *   <li>사용한 기술 스택(technologies)과 모집 기간(recruitPeriod) 관리</li>
 *   <li>시작가(startPrice) 및 현재 최고 입찰가(currentPrice) 추적</li>
 *   <li>상태(status): "구인중" 또는 "마감" 구분</li>
 *   <li>작성자(user)와 입찰 목록(bids) 연관 관계 설정</li>
 *   <li>생성 일시(createdAt)를 Auditing 기능으로 자동 기록</li>
 * </ul>
 * </p>
 */
@Entity
@Getter @Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)  // 생성 일시 자동 기록(Auditing) 활성화
public class Board {

    /**
     * 게시물의 고유 식별자(ID)
     * - @Id: 기본 키 설정
     * - @GeneratedValue: DB Auto-Increment 전략 지정 (IDENTITY)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 프로젝트 제목
     * - null 허용하지 않음(nullable = false)
     */
    @Column(nullable = false)
    private String title;

    /**
     * 프로젝트 마감 기한
     * - LocalDateTime 타입으로 날짜와 시각 정보 모두 저장
     */
    private LocalDateTime dueDate;

    /**
     * 프로젝트 상세 설명
     * - 최대 2000자까지 허용(length = 2000)
     */
    @Column(length = 2000)
    private String description;

    /**
     * 프로젝트에서 요구하는 기술 스택 목록
     * - @ElementCollection: 기본 타입 또는 임베디드 타입 컬렉션 매핑
     * - @CollectionTable: 별도 테이블(board_technologies)로 저장, FK는 board_id
     */
    @ElementCollection
    @CollectionTable(
            name = "board_technologies",
            joinColumns = @JoinColumn(name = "board_id")
    )
    @Column(name = "technology")
    private List<String> technologies = new ArrayList<>();

    /**
     * 모집 기간(단위: 일)
     * - Integer 타입으로 일 수를 저장
     */
    private Integer recruitPeriod;

    /**
     * 경매 시작가(단위: 원)
     * - null 허용하지 않음
     */
    @Column(nullable = false)
    private Long startPrice;

    /**
     * 현재 최고 입찰가(단위: 원)
     * - null 허용하지 않음
     * - BidService에서 입찰 발생 시 자동 갱신
     */
    @Column(nullable = false)
    private Long currentPrice;

    /**
     * 게시물 상태
     * - "구인중" 또는 "마감" 문자열로 관리
     * - 상태 전이는 서비스 레이어에서 제어
     */
    @Column(nullable = false)
    private String status;

    /**
     * 게시물 생성 일시
     * - @CreatedDate: 최초 저장 시점에 자동으로 값 주입
     * - updatable = false: 한 번 설정된 후 변경 불가
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * 게시물 작성자(user)와 다대일 관계
     * - FetchType.LAZY: 실제로 사용할 때 로딩
     * - @JsonIgnore: 순환 참조 방지 및 응답 시 사용자 정보 노출 방지
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    /**
     * 이 게시물에 달린 모든 입찰(Bid) 목록
     * - mappedBy = "board": Bid 엔티티의 board 필드와 양방향 매핑
     * - cascade = REMOVE: 게시물 삭제 시 연관된 입찰도 함께 삭제
     * - orphanRemoval = true: 컬렉션에서 제거된 Bid도 삭제
     */
    @OneToMany(
            mappedBy = "board",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<Bid> bids = new ArrayList<>();

}
