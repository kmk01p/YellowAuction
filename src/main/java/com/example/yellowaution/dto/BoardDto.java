package com.example.yellowaution.dto;

import com.example.yellowaution.domain.Board;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * BoardDto 클래스는 Board 엔티티의 데이터를 클라이언트에
 * 전송하기 위한 Data Transfer Object입니다.
 *
 * <p>주요 필드:
 * <ul>
 *   <li>기본 게시물 정보(id, title, description 등)</li>
 *   <li>모집 마감일(dueDate), 생성일(createdAt)</li>
 *   <li>기술 스택(technologies), 모집 기간(recruitPeriod)</li>
 *   <li>시작가(startPrice) 및 현재 최고 입찰가(currentPrice)</li>
 *   <li>게시물 상태(status): "구인중" 또는 "마감"</li>
 *   <li>작성자 정보(userId, username)</li>
 * </ul>
 * </p>
 */
@Getter
@Setter
@Builder
public class BoardDto {

    /** 게시물 고유 식별자 */
    private Long id;

    /** 게시물 제목 */
    private String title;

    /**
     * 모집 마감일시
     * - ISO 8601 형식: "yyyy-MM-dd'T'HH:mm"
     * - Jackson 직렬화 시 지정된 패턴으로 변환
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dueDate;

    /** 게시물 상세 설명 */
    private String description;

    /** 요구 기술 스택 목록 */
    private List<String> technologies;

    /** 모집 기간(단위: 일) */
    private Integer recruitPeriod;

    /** 경매 시작가(단위: 원) */
    private Long startPrice;

    /** 현재 최고 입찰가(단위: 원) */
    private Long currentPrice;

    /** 게시물 상태 ("구인중" 또는 "마감") */
    private String status;

    /** 게시물 생성 일시 */
    private LocalDateTime createdAt;

    /** 게시물 작성자 고유 식별자 */
    private Long userId;

    /** 게시물 작성자 사용자명 */
    private String username;

    /**
     * 모든 필드를 인자로 받는 생성자.
     * Lombok @Builder와 함께 사용하여 유연하게 DTO 인스턴스를 생성합니다.
     */
    public BoardDto(Long id,
                    String title,
                    LocalDateTime dueDate,
                    String description,
                    List<String> technologies,
                    Integer recruitPeriod,
                    Long startPrice,
                    Long currentPrice,
                    String status,
                    LocalDateTime createdAt,
                    Long userId,
                    String username) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.description = description;
        this.technologies = technologies;
        this.recruitPeriod = recruitPeriod;
        this.startPrice = startPrice;
        this.currentPrice = currentPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.userId = userId;
        this.username = username;
    }

    /**
     * Board 엔티티를 DTO로 변환하는 정적 팩토리 메서드.
     *
     * @param board 변환할 Board 엔티티 객체
     * @return BoardDto 생성된 DTO 객체
     */
    public static BoardDto from(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .dueDate(board.getDueDate())
                .description(board.getDescription())
                .technologies(board.getTechnologies())
                .recruitPeriod(board.getRecruitPeriod())
                .startPrice(board.getStartPrice())
                .currentPrice(board.getCurrentPrice())
                .status(board.getStatus())
                .createdAt(board.getCreatedAt())
                .userId(board.getUser() != null ? board.getUser().getId() : null)
                .username(board.getUser() != null ? board.getUser().getUsername() : null)
                .build();
    }
}
