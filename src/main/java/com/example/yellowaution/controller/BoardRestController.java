package com.example.yellowaution.controller;

import com.example.yellowaution.domain.Board;
import com.example.yellowaution.dto.ApiResponse;
import com.example.yellowaution.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardRestController {
    private final BoardService service;

    @GetMapping
    public ApiResponse list() {
        return new ApiResponse(service.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse get(@PathVariable Long id) {
        return new ApiResponse(service.getById(id));
    }

    @PostMapping
    public ApiResponse create(@RequestBody Board board) {
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