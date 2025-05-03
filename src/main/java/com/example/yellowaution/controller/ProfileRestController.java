package com.example.yellowaution.controller;

import com.example.yellowaution.dto.ApiResponse;
import com.example.yellowaution.dto.ProfileDto;
import com.example.yellowaution.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/com/profile")
@RequiredArgsConstructor
public class ProfileRestController {
    private final ProfileService service;

    // 1) 리스트 조회
    @GetMapping
    public ApiResponse list() {
        return new ApiResponse(service.getList());
    }

    // 2) 프로필 생성
    @PostMapping
    public ApiResponse create(@RequestBody ProfileDto dto) {
        return new ApiResponse(service.create(dto));
    }

    // 3) 단건 조회
    @GetMapping("/{id}")
    public ApiResponse get(@PathVariable Long id) {
        return new ApiResponse(service.get(id));
    }

    // 4) 프로필 수정
    @PutMapping("/{id}")
    public ApiResponse update(@PathVariable Long id,
                              @RequestBody ProfileDto dto) {
        return new ApiResponse(service.update(id, dto));
    }


    // 5) 프로필 삭제
    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        service.delete(id);
        return new ApiResponse(null);
    }
}
