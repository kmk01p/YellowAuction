package com.example.yellowaution.controller;

import com.example.yellowaution.domain.User;
import com.example.yellowaution.dto.*;
import com.example.yellowaution.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * AuthRestController 클래스는 RESTful API 방식으로
 * 인증 관련 기능(회원가입, 로그인, 로그아웃, 현재 사용자 조회)을 제공합니다.
 *
 * <p>매핑:
 * <ul>
 *   <li>POST   /api/auth/register : 회원가입 처리</li>
 *   <li>POST   /api/auth/login    : 로그인 처리</li>
 *   <li>POST   /api/auth/logout   : 로그아웃 처리</li>
 *   <li>GET    /api/auth/me       : 현재 로그인 사용자 정보 조회</li>
 * </ul>
 * </p>
 *
 * <p>예외 처리:
 * - 회원가입 시 중복 사용자 등록 시 CONFLICT(409) 응답
 * - 로그인 실패 시 UNAUTHORIZED(401) 응답
 * - 인증되지 않은 상태에서 /me 요청 시 UNAUTHORIZED(401) 응답</p>
 */
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    /**
     * 사용자 관련 비즈니스 로직을 처리하는 서비스 객체.
     * - register, login 메서드 제공
     */
    private final UserService userService;

    /**
     * 생성자 주입(Constructor Injection)을 통해 UserService를 주입받습니다.
     *
     * @param userService 사용자 관리 로직을 수행하는 서비스 객체
     */
    public AuthRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원가입 요청을 처리합니다.
     *
     * @param dto 회원가입 정보(username, password, email 등)를 담은 DTO
     * @return 성공 시 200 OK와 ApiResponse(true, "회원가입 성공")
     *         실패(중복 등) 시 409 CONFLICT와 ApiResponse(false, 에러 메시지)
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserRegisterDto dto) {
        try {
            // 서비스 레이어에 회원가입 로직 위임
            userService.register(dto);
            return ResponseEntity.ok(new ApiResponse(true, "회원가입 성공"));
        } catch (IllegalArgumentException e) {
            // 중복 회원 등 예외 발생 시 409 상태 코드 반환
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * 로그인 요청을 처리합니다.
     *
     * @param req     로그인 정보(username, password)를 담은 DTO
     * @param session HTTP 세션 객체 (성공 시 사용자 정보 저장)
     * @return 성공 시 200 OK와 UserDto(JSON 형태 사용자 정보)
     *         실패 시 401 UNAUTHORIZED와 ApiResponse(false, "로그인 실패")
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req, HttpSession session) {
        try {
            // 아이디, 비밀번호 검증 후 User 객체 반환
            User user = userService.login(req.getUsername(), req.getPassword());
            // 세션에 로그인 사용자 정보 저장
            session.setAttribute("loginUser", user);

            // 클라이언트에 반환할 최소 정보만 담은 DTO 생성
            UserDto dto = new UserDto(
                    user.getId(),
                    user.getUsername(),
                    user.getRole(),
                    user.getUserType()
            );
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            // 로그인 실패 시 401 상태 코드와 에러 메시지 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "로그인 실패"));
        }
    }

    /**
     * 로그아웃 요청을 처리합니다.
     *
     * @param session HTTP 세션 객체 (세션 무효화)
     * @return 200 OK와 ApiResponse(true, "로그아웃 되었습니다.")
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpSession session) {
        // 세션 무효화로 모든 로그인 정보 제거
        session.invalidate();
        return ResponseEntity.ok(new ApiResponse(true, "로그아웃 되었습니다."));
    }

    /**
     * 현재 로그인한 사용자의 정보를 조회합니다.
     *
     * @param session HTTP 세션 객체에서 loginUser 속성 조회
     * @return 인증된 사용자면 200 OK와 UserDto,
     *         인증되지 않은 상태면 401 UNAUTHORIZED와 ApiResponse(false, "로그인 필요")
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            // 로그인 정보가 없으면 인증 필요 응답
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "로그인 필요"));
        }
        // 로그인된 사용자 정보를 DTO에 담아 반환
        UserDto dto = new UserDto(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getUserType()
        );
        return ResponseEntity.ok(dto);
    }
}
