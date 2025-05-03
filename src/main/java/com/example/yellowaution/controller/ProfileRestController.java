package com.example.yellowaution.controller;

import com.example.yellowaution.dto.ApiResponse;
import com.example.yellowaution.dto.ProfileDto;
import com.example.yellowaution.security.UserPrincipal;
import com.example.yellowaution.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/com/profile")
@RequiredArgsConstructor
public class ProfileRestController {

    private final ProfileService service;

    // 1) 리스트 조회 (내 프로필만)
    @GetMapping
    public ApiResponse list(@AuthenticationPrincipal UserPrincipal principal) {
        return new ApiResponse(service.getList(principal));
    }

    // 2) 생성
    @PostMapping
    public ApiResponse create(@RequestBody ProfileDto dto,
                              @AuthenticationPrincipal UserPrincipal principal) {
        return new ApiResponse(service.create(dto, principal));
    }

    // 3) 단건 조회
    @GetMapping("/{id}")
    public ApiResponse get(@PathVariable Long id,
                           @AuthenticationPrincipal UserPrincipal principal) {
        return new ApiResponse(service.get(id, principal));
    }

    // 4) 수정
    @PutMapping("/{id}")
    public ApiResponse update(@PathVariable Long id,
                              @RequestBody ProfileDto dto,
                              @AuthenticationPrincipal UserPrincipal principal) {
        return new ApiResponse(service.update(id, dto, principal));
    }

    // 5) 삭제
    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Long id,
                              @AuthenticationPrincipal UserPrincipal principal) {
        service.delete(id, principal);
        return new ApiResponse(null);
    }
}
