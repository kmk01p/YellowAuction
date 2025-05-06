package com.example.yellowaution.dto;

import lombok.*;

/**
 * 관리자 화면에서 전체 입찰 내역을 조회할 때 사용하는 DTO 클래스입니다.
 *
 * <p>주요 용도:
 * <ul>
 *   <li>각 입찰(Bid) 정보를 관리자가 보기 쉬운 형태로 가공하여 전달</li>
 *   <li>게시글 제목, 입찰자 아이디, 입찰 금액, 입찰 시각 등을 포함</li>
 * </ul>
 * </p>
 */
@Getter
@Setter
@Data
@AllArgsConstructor
public class BidAdminDto {

    /** 입찰 고유 식별자 (Bid 엔티티의 ID) */
    private Long id;

    /** 입찰이 등록된 게시글의 제목 */
    private String boardTitle;

    /** 입찰을 수행한 사용자 계정의 아이디(username) */
    private String bidderUsername;

    /** 입찰 금액 (원 단위) */
    private Long amount;

    /** 입찰이 발생한 시각 (예: "2025-05-06 14:30:00" 형식의 문자열) */
    private String createdAt;
}
