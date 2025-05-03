package com.example.yellowaution.controller;

import com.example.yellowaution.domain.Board;
import com.example.yellowaution.domain.User;
import com.example.yellowaution.dto.ApiResponse;
import com.example.yellowaution.dto.BoardDto;
import com.example.yellowaution.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardRestController {
    private final BoardService service;

    // 전체 조회
    @GetMapping
    public ApiResponse list() {
        return new ApiResponse(service.findAll());
    }

    // 검색 전용
    @GetMapping("/search")
    public ApiResponse search(@RequestParam String keyword) {
        return new ApiResponse(service.searchByKeyword(keyword));
    }

    // 정렬 전용
    @GetMapping("/sort")
    public ApiResponse sort(@RequestParam String sort) {
        return new ApiResponse(service.sortByCriteria(sort));
    }


    @GetMapping("/{id}")
    public ApiResponse get(@PathVariable Long id) {
        return new ApiResponse(service.getById(id));
    }

    @PostMapping
    public ApiResponse create(@RequestBody Board board, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        board.setUser(user);
        return new ApiResponse(service.create(board));
    }

    @PutMapping("/{id}")
    public ApiResponse update(@PathVariable Long id, @RequestBody Board dto) {
        return new ApiResponse(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        service.delete(id);
        return new ApiResponse(null);
    }

    @PutMapping("/{id}/close")
    public ApiResponse close(@PathVariable Long id) {
        return new ApiResponse(service.close(id));
    }

    @PostMapping("/{id}/bid")
    public ApiResponse bid(@PathVariable Long id, @RequestBody Map<String, Long> body) {
        return new ApiResponse(service.bid(id, body.getOrDefault("amount", 0L)));
    }


}