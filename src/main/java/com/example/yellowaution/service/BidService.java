package com.example.yellowaution.service;

import com.example.yellowaution.dto.BidAdminDto;

import java.util.List;

public interface BidService {
    List<BidAdminDto> findAllForAdmin();
    List<BidAdminDto> findAllForAdminByBoardId(Long boardId);
}
