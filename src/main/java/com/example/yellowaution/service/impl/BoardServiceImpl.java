package com.example.yellowaution.service.impl;

import com.example.yellowaution.config.AuctionConstants;       // 경매 상수값 참조
import com.example.yellowaution.domain.Bid;
import com.example.yellowaution.domain.Board;
import com.example.yellowaution.domain.User;
import com.example.yellowaution.dto.BoardDto;
import com.example.yellowaution.mapper.BoardMapper;
import com.example.yellowaution.repository.BidRepository;
import com.example.yellowaution.repository.BoardRepository;
import com.example.yellowaution.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * BoardServiceImpl 클래스는 BoardService 인터페이스를 구현한 서비스 계층 클래스입니다.
 *
 * <p>주요 기능:
 * <ul>
 *   <li>전체 게시물 조회, 키워드 검색, 정렬</li>
 *   <li>단건 조회, 생성, 수정, 삭제, 마감(close)</li>
 *   <li>입찰 로직 처리 (bid): 상태 검사, 연속 입찰 방지, 금액 검증, 저장 후 현재가 갱신</li>
 * </ul>
 * </p>
 *
 * <p>트랜잭션 관리:
 * - 클래스 레벨에 @Transactional이 적용되어 모든 공개 메서드가 트랜잭션 범위로 실행됩니다.</p>
 */
@Service
@RequiredArgsConstructor  // final 필드에 대해 생성자 자동 주입
@Transactional
public class BoardServiceImpl implements BoardService {
    private final BoardRepository repository;    // JPA 리포지토리
    private final BoardMapper boardMapper;       // MyBatis 매퍼
    private final BidRepository bidRepository;   // 입찰 리포지토리

    /**
     * 전체 게시물을 DTO 형태로 조회합니다.
     *
     * @return 게시물 리스트 (BoardDto)
     */
    @Override
    public List<BoardDto> findAll() {
        return repository.findAll().stream()
                .map(BoardDto::from)
                .toList();
    }

    /**
     * 키워드로 제목/설명 검색 후 DTO 형태로 반환합니다.
     *
     * @param keyword 검색 키워드
     * @return 검색 결과 리스트 (BoardDto)
     */
    @Override
    public List<BoardDto> searchByKeyword(String keyword) {
        var boards = boardMapper.searchByKeyword(keyword);
        return boards.stream()
                .map(BoardDto::from)
                .toList();
    }

    /**
     * 정렬 기준에 따라 게시물 조회 후 DTO 형태로 반환합니다.
     *
     * @param sort 정렬 기준 문자열 (예: "date", "price" 등)
     * @return 정렬된 게시물 리스트 (BoardDto)
     */
    @Override
    public List<BoardDto> sortByCriteria(String sort) {
        var boards = boardMapper.sortByCriteria(sort);
        return boards.stream()
                .map(BoardDto::from)
                .toList();
    }

    /**
     * ID로 게시물을 조회합니다.
     *
     * @param id 조회할 게시물 ID
     * @return 조회된 Board 엔티티
     * @throws IllegalArgumentException 해당 ID의 게시물이 없을 경우
     */
    @Override
    public Board getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트가 없습니다. id=" + id));
    }

    /**
     * 새 게시물을 등록합니다.
     * <ol>
     *   <li>시작가(MINIMUM_STARTING_BID) 이상인지 검증</li>
     *   <li>currentPrice를 시작가로 초기화</li>
     *   <li>DB에 저장</li>
     * </ol>
     *
     * @param board 등록할 Board 엔티티 (title, dueDate, description 등 포함)
     * @return 저장된 Board 엔티티
     * @throws IllegalArgumentException 시작가가 최소 금액 미만일 경우
     */
    @Override
    public Board create(Board board) {
        // 1) 시작가 최소 금액 제약 검사
        if (board.getStartPrice() < AuctionConstants.MINIMUM_STARTING_BID) {
            throw new IllegalArgumentException(
                    "경매 시작가는 최소 " + AuctionConstants.MINIMUM_STARTING_BID + "원 이상이어야 합니다."
            );
        }

        // 2) currentPrice를 시작가로 초기화
        board.setCurrentPrice(board.getStartPrice());
        return repository.save(board);
    }

    /**
     * 기존 게시물을 수정합니다.
     * <ol>
     *   <li>작성자 본인 여부 확인</li>
     *   <li>제목, 마감일, 설명, 기술 스택, 모집 기간, 시작가, 상태 등 업데이트</li>
     * </ol>
     *
     * @param id  수정할 게시물 ID
     * @param dto 수정 데이터가 담긴 Board 객체
     * @return 수정된 Board 엔티티
     * @throws SecurityException      작성자가 아닐 경우
     * @throws IllegalArgumentException 해당 게시물이 없을 경우
     */
    @Override
    public Board update(Long id, Board dto) {
        Board b = getById(id);
        Long loginUserId = dto.getUser().getId();

        // 작성자만 수정 가능
        if (!b.getUser().getId().equals(loginUserId)) {
            throw new SecurityException("작성자만 수정할 수 있습니다.");
        }

        // 변경 가능한 필드 업데이트
        b.setTitle(dto.getTitle());
        b.setDueDate(dto.getDueDate());
        b.setDescription(dto.getDescription());
        b.setTechnologies(dto.getTechnologies());
        b.setRecruitPeriod(dto.getRecruitPeriod());
        b.setStartPrice(dto.getStartPrice());
        b.setStatus(dto.getStatus());

        return repository.save(b);
    }

    /**
     * 게시물을 삭제합니다.
     *
     * @param id 삭제할 게시물 ID
     */
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * 게시물 상태를 '마감'으로 변경합니다.
     *
     * @param id 마감할 게시물 ID
     * @return 상태가 변경된 Board 엔티티
     * @throws IllegalArgumentException 해당 게시물이 없을 경우
     */
    @Override
    public Board close(Long id) {
        Board b = getById(id);
        b.setStatus("마감");
        return repository.save(b);
    }

    /**
     * 게시물에 입찰을 수행합니다.
     * <ol>
     *   <li>게시물 존재 및 상태('구인중') 확인</li>
     *   <li>로그인 사용자 세션 확인</li>
     *   <li>연속 입찰 금지: 마지막 입찰자가 본인인지 검사</li>
     *   <li>프리랜서 단위 제약: 입찰액이 FREELANCER_BID_INCREMENT 배수인지 검사</li>
     *   <li>입찰액이 현재가를 초과하는지 검사</li>
     *   <li>Bid 엔티티 생성·저장, Board.currentPrice 업데이트</li>
     * </ol>
     *
     * @param boardId 입찰할 게시물 ID
     * @param amount  입찰 금액
     * @param session HTTP 세션 (loginUser 속성에서 User 가져옴)
     * @return 업데이트된 Board 엔티티
     * @throws SecurityException      미로그인 또는 작성자 연속 입찰 시
     * @throws IllegalArgumentException 입찰 가능 상태가 아닐 때, 금액 검증 실패 시
     */
    @Override
    public Board bid(Long boardId, Long amount, HttpSession session) {
        Board board = getById(boardId);

        // 1) 상태 확인: '구인중'이 아니면 입찰 불가
        if (!"구인중".equals(board.getStatus())) {
            throw new IllegalArgumentException("이미 마감된 프로젝트입니다.");
        }

        // 2) 로그인 사용자 세션에서 꺼내기
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new SecurityException("로그인한 사용자만 입찰할 수 있습니다.");
        }

        // 3) 연속 입찰 금지: 마지막 입찰자가 본인이면 예외
        bidRepository.findFirstByBoardOrderByCreatedAtDesc(board)
                .ifPresent(last -> {
                    if (last.getUser().getId().equals(loginUser.getId())) {
                        throw new IllegalArgumentException(
                                "현재 당신이 최신 입찰자입니다. 다른 사람이 입찰할 때까지 기다려주세요."
                        );
                    }
                });

        // 4) 프리랜서 단위 제약: 지정 단위 금액 배수여야 함
        if ("FREELANCER".equalsIgnoreCase(loginUser.getUserType())
                && (amount % AuctionConstants.FREELANCER_BID_INCREMENT != 0)) {
            throw new IllegalArgumentException(
                    "프리랜서는 " + AuctionConstants.FREELANCER_BID_INCREMENT + "원 단위로만 입찰 가능합니다."
            );
        }

        // 5) 입찰액이 현재가 초과하는지 확인
        long current = board.getCurrentPrice();
        if (amount <= current) {
            throw new IllegalArgumentException(
                    "입찰액은 현재가(" + current + "원) 보다 커야 합니다."
            );
        }

        // 6) Bid 엔티티 생성 및 저장
        Bid bid = new Bid();
        bid.setAmount(amount);
        bid.setBoard(board);
        bid.setUser(loginUser);
        bid.setCreatedAt(LocalDateTime.now());
        bidRepository.save(bid);

        // 7) Board.currentPrice 업데이트 및 저장
        board.setCurrentPrice(amount);
        return repository.save(board);
    }
}
