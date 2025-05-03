package com.example.yellowaution.controller;

import com.example.yellowaution.domain.User;
import com.example.yellowaution.dto.*;
import com.example.yellowaution.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final UserService userService;
    public AuthRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserRegisterDto dto) {
        try {
            userService.register(dto); // 전체 DTO 전달
            return ResponseEntity.ok(new ApiResponse(true, "회원가입 성공"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req, HttpSession session) {
        try {
            User user = userService.login(req.getUsername(), req.getPassword());
            session.setAttribute("loginUser", user);
            UserDto dto = new UserDto(
                    user.getId(), user.getUsername(),
                    user.getRole(), user.getUserType()
            );
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "로그인 실패"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(new ApiResponse(true, "로그아웃 되었습니다."));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "로그인 필요"));
        }

        UserDto dto = new UserDto(
                user.getId(), user.getUsername(),
                user.getRole(), user.getUserType()
        );
        return ResponseEntity.ok(dto);
    }

}
