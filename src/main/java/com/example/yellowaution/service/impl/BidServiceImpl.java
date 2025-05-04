package com.example.yellowaution.service.impl;

import com.example.yellowaution.domain.Bid;
import com.example.yellowaution.dto.BidAdminDto;
import com.example.yellowaution.repository.BidRepository;
import com.example.yellowaution.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;

    @Override
    public List<BidAdminDto> findAllForAdmin() {
        return bidRepository.findAll().stream()
                .map(bid -> new BidAdminDto(
                        bid.getId(),
                        bid.getBoard().getTitle(),
                        bid.getUser().getUsername(),
                        bid.getAmount(),
                        bid.getCreatedAt() != null ? bid.getCreatedAt().toString() : null
                ))
                .toList();
    }

}
