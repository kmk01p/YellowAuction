package com.example.yellowaution.controller;

import com.example.yellowaution.service.BidService;
import com.example.yellowaution.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 전용 REST API 컨트롤러 클래스입니다.
 *
 * <p>ADMIN 권한을 가진 사용자만 접근할 수 있도록 설정되어 있으며,
 * 사용자 목록 및 입찰 내역 조회 기능을 제공합니다.</p>
 *
 * <p>매핑:
 * <ul>
 *   <li>GET /api/admin/users → 전체 사용자 목록 조회</li>
 *   <li>GET /api/admin/bids  → 전체 입찰 내역 조회</li>
 * </ul>
 * </p>
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")  // ADMIN 권한 보유자만 접근 허용
public class AdminRestController {

    /**
     * 사용자 관련 비즈니스 로직을 수행하는 서비스.
     * - 관리자용 사용자 목록 조회 메서드(findAllForAdmin) 제공
     */
    private final UserService userService;

    /**
     * 입찰 관련 비즈니스 로직을 수행하는 서비스.
     * - 관리자용 전체 입찰 내역 조회 메서드(findAllForAdmin) 제공
     */
    private final BidService bidService;

    /**
     * 전체 사용자 목록을 조회하여 반환합니다.
     *
     * @return HTTP 200 OK와 함께 관리자용 전체 사용자 목록 데이터
     */
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.findAllForAdmin());
    }

    /**
     * 전체 입찰 내역을 조회하여 반환합니다.
     *
     * @return HTTP 200 OK와 함께 관리자용 전체 입찰 내역 데이터
     */
    @GetMapping("/bids")
    public ResponseEntity<?> getAllBids() {
        return ResponseEntity.ok(bidService.findAllForAdmin());
    }
}
