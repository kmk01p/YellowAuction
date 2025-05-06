package com.example.yellowaution.service.impl;

import com.example.yellowaution.domain.Bid;
import com.example.yellowaution.dto.BidAdminDto;
import com.example.yellowaution.repository.BidRepository;
import com.example.yellowaution.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * BidServiceImpl 클래스는 BidService 인터페이스를 구현한 서비스 레이어 클래스입니다.
 *
 * <p>관리자 페이지에서 전체 입찰 내역 조회 및 특정 게시물별 입찰 내역 조회 기능을 제공하며,
 * 조회 결과를 관리자가 보기 쉬운 형태의 BidAdminDto로 변환합니다.</p>
 */
@Service
@RequiredArgsConstructor  // final 필드를 자동으로 생성자 주입
public class BidServiceImpl implements BidService {

    /**
     * Bid 엔티티에 대한 CRUD 및 커스텀 조회 메서드를 제공하는 레포지토리
     */
    private final BidRepository bidRepository;

    /**
     * 관리자용 전체 입찰 내역을 조회합니다.
     *
     * <p>동작:
     * <ol>
     *   <li>bidRepository.findAll()로 모든 Bid 엔티티를 조회</li>
     *   <li>Java Stream API를 사용해 각 Bid를 BidAdminDto로 매핑</li>
     *   <li>BidAdminDto 생성자에 입찰 ID, 게시글 제목, 입찰자 아이디, 입찰 금액, 생성 시각(문자열) 전달</li>
     *   <li>문자열 변환 시 toString()을 사용하므로, LocalDateTime 기본 포맷(ISO-8601)이 적용</li>
     * </ol>
     *
     * @return 관리자가 볼 수 있는 형태의 BidAdminDto 리스트
     */
    @Override
    public List<BidAdminDto> findAllForAdmin() {
        return bidRepository.findAll().stream()
                .map(bid -> new BidAdminDto(
                        bid.getId(),                             // 입찰 고유 ID
                        bid.getBoard().getTitle(),               // 입찰 대상 게시글 제목
                        bid.getUser().getUsername(),             // 입찰자 사용자명
                        bid.getAmount(),                         // 입찰 금액
                        bid.getCreatedAt() != null               // 생성 시각이 null이 아니면
                                ? bid.getCreatedAt().toString() // LocalDateTime.toString() (ISO-8601)
                                : null                         // null인 경우 null 반환
                ))
                .toList();  // Java 16+에서 사용 가능한 Stream 결과 List 변환 메서드
    }

    /**
     * 특정 게시물(boardId)에 대한 입찰 내역을 최신 순(생성 시각 내림차순)으로 조회합니다.
     *
     * <p>동작:
     * <ol>
     *   <li>bidRepository.findByBoardIdOrderByCreatedAtDesc(boardId) 호출</li>
     *   <li>반환된 리스트를 Stream으로 변환한 뒤, toDto() 헬퍼 메서드로 매핑</li>
     *   <li>toDto()에서는 DateTimeFormatter를 사용해 생성 시각을 "yyyy-MM-dd HH:mm:ss" 형식의 문자열로 포맷팅</li>
     * </ol>
     *
     * @param boardId 조회 대상 게시물의 고유 식별자
     * @return 최신 순으로 정렬된 BidAdminDto 리스트
     */
    @Override
    public List<BidAdminDto> findAllForAdminByBoardId(Long boardId) {
        return bidRepository.findByBoardIdOrderByCreatedAtDesc(boardId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * Bid 엔티티를 BidAdminDto로 변환하는 헬퍼 메서드입니다.
     *
     * <p>생성 시각(createdAt)을 "yyyy-MM-dd HH:mm:ss" 패턴의 문자열로 포맷팅하여
     * 관리자가 보기 편한 형태로 제공합니다.</p>
     *
     * @param bid 변환할 Bid 엔티티 객체
     * @return BidAdminDto로 변환된 결과 객체
     */
    private BidAdminDto toDto(Bid bid) {
        return new BidAdminDto(
                bid.getId(),                                        // 입찰 ID
                bid.getBoard().getTitle(),                          // 게시글 제목
                bid.getUser().getUsername(),                        // 입찰자 ID
                bid.getAmount(),                                    // 입찰 금액
                bid.getCreatedAt() != null                          // 생성 시각이 null이 아니면
                        ? bid.getCreatedAt()
                        .format(DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss")) // 지정된 포맷으로 변환
                        : null                                       // null인 경우 null 반환
        );
    }

}
