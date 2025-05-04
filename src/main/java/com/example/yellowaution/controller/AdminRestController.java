package com.example.yellowaution.controller;

import com.example.yellowaution.service.BidService;
import com.example.yellowaution.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")  // 여기 수정
public class AdminRestController {

    private final UserService userService;
    private final BidService bidService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.findAllForAdmin());
    }

    @GetMapping("/bids")
    public ResponseEntity<?> getAllBids() {
        return ResponseEntity.ok(bidService.findAllForAdmin());
    }
}
