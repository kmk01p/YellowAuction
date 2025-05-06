package com.example.yellowaution.controller;

import com.example.yellowaution.domain.Board;
import com.example.yellowaution.domain.User;
import com.example.yellowaution.dto.ApiResponse;
import com.example.yellowaution.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * BoardRestController 클래스는 RESTful 방식으로 게시판(Board) 관련
 * CRUD 및 검색/정렬/입찰 기능을 제공합니다.
 *
 * <p>매핑:
 * <ul>
 *   <li>GET    /api/board             : 전체 게시글 목록 조회</li>
 *   <li>GET    /api/board/search      : 키워드 검색</li>
 *   <li>GET    /api/board/sort        : 정렬 기준별 목록 조회</li>
 *   <li>GET    /api/board/{id}        : 단일 게시글 조회</li>
 *   <li>POST   /api/board             : 게시글 생성</li>
 *   <li>PUT    /api/board/{id}        : 게시글 수정</li>
 *   <li>DELETE /api/board/{id}        : 게시글 삭제</li>
 *   <li>PUT    /api/board/{id}/close  : 게시글 마감 처리</li>
 *   <li>POST   /api/board/{id}/bid    : 해당 게시글에 입찰</li>
 * </ul>
 * </p>
 *
 * <p>세션에서 로그인 사용자(loginUser)를 꺼내와 글 작성 및 입찰 시
 * 작성자 정보를 설정하는 로직을 포함합니다.</p>
 */
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardRestController {

    /**
     * 게시글 비즈니스 로직을 처리하는 서비스.
     * - findAll, searchByKeyword, sortByCriteria 등 메서드 제공
     */
    private final BoardService service;

    /**
     * 전체 게시글 목록을 조회합니다.
     *
     * @return ApiResponse: 전체 게시글 리스트를 담은 응답 객체
     */
    @GetMapping
    public ApiResponse list() {
        return new ApiResponse(service.findAll());
    }

    /**
     * 키워드로 게시글을 검색합니다.
     *
     * @param keyword 검색에 사용할 키워드 (제목/내용 등)
     * @return ApiResponse: 검색 결과 게시글 리스트를 담은 응답 객체
     */
    @GetMapping("/search")
    public ApiResponse search(@RequestParam String keyword) {
        return new ApiResponse(service.searchByKeyword(keyword));
    }

    /**
     * 특정 기준으로 게시글 목록을 정렬하여 조회합니다.
     *
     * @param sort 정렬 기준 (예: "date", "bidCount", "price" 등)
     * @return ApiResponse: 정렬된 게시글 리스트를 담은 응답 객체
     */
    @GetMapping("/sort")
    public ApiResponse sort(@RequestParam String sort) {
        return new ApiResponse(service.sortByCriteria(sort));
    }

    /**
     * 단일 게시글을 ID로 조회합니다.
     *
     * @param id 조회할 게시글의 고유 식별자
     * @return ApiResponse: 조회된 게시글 데이터를 담은 응답 객체
     */
    @GetMapping("/{id}")
    public ApiResponse get(@PathVariable Long id) {
        Board board = service.getById(id);
        return new ApiResponse(board);
    }

    /**
     * 새로운 게시글을 생성합니다.
     * 세션에 저장된 로그인 사용자를 글 작성자로 설정합니다.
     *
     * @param board   요청 바디로 전달된 게시글 정보(제목, 내용, 예산 등)
     * @param session HttpSession: 세션에서 loginUser 속성(현재 로그인 사용자) 조회
     * @return ApiResponse: 생성된 게시글 데이터를 담은 응답 객체
     */
    @PostMapping
    public ApiResponse create(@RequestBody Board board, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        board.setUser(user);
        Board created = service.create(board);
        return new ApiResponse(created);
    }

    /**
     * 기존 게시글을 수정합니다.
     *
     * @param id  수정할 게시글의 ID
     * @param dto 요청 바디로 전달된 수정할 게시글 정보
     * @return ApiResponse: 수정된 게시글 데이터를 담은 응답 객체
     */
    @PutMapping("/{id}")
    public ApiResponse update(
            @PathVariable Long id,
            @RequestBody Board dto,
            HttpSession session   // 세션 파라미터 추가
    ) {
        // 1) 세션에서 로그인 사용자 가져오기
        User loginUser = (User) session.getAttribute("loginUser");
        // 2) dto에 집어넣기
        dto.setUser(loginUser);

        // 3) 업데이트
        Board updated = service.update(id, dto);
        return new ApiResponse(updated);
    }

    /**
     * 특정 게시글을 삭제합니다.
     *
     * @param id 삭제할 게시글의 ID
     * @return ApiResponse: 삭제 후 응답 객체(null 데이터)
     */
    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        service.delete(id);
        return new ApiResponse(null);
    }

    /**
     * 특정 게시글을 마감(종료) 처리합니다.
     *
     * @param id 마감할 게시글의 ID
     * @return ApiResponse: 마감 처리된 게시글 데이터를 담은 응답 객체
     */
    @PutMapping("/{id}/close")
    public ApiResponse close(@PathVariable Long id) {
        Board closed = service.close(id);
        return new ApiResponse(closed);
    }

    /**
     * 특정 게시글에 입찰을 수행합니다.
     *
     * @param id      입찰할 게시글의 ID
     * @param body    요청 바디에서 입찰 금액(amount) 정보를 포함한 Map
     * @param session HttpSession: 세션에서 loginUser 속성으로 현재 로그인 사용자 조회
     * @return ApiResponse: 입찰 결과(Bid 등)를 담은 응답 객체
     */
    @PostMapping("/{id}/bid")
    public ApiResponse bid(
            @PathVariable Long id,
            @RequestBody Map<String, Long> body,
            HttpSession session) {

        Long amount = body.getOrDefault("amount", 0L);
        Object bidResult = service.bid(id, amount, session);
        return new ApiResponse(bidResult);
    }

}
