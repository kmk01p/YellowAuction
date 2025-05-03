package com.example.yellowaution.service.impl;

import com.example.yellowaution.domain.Board;
import com.example.yellowaution.repository.BoardRepository;
import com.example.yellowaution.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {
    private final BoardRepository repository;

    @Override
    public List<Board> findAll() {
        return repository.findAll();
    }

    @Override
    public Board getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트가 없습니다. id=" + id));
    }

    @Override
    public Board create(Board board) {
        board.setCurrentPrice(board.getStartPrice());
        return repository.save(board);
    }

    @Override
    public Board update(Long id, Board dto) {
        Board b = getById(id);
        b.setTitle(dto.getTitle());
        b.setDueDate(dto.getDueDate());
        b.setDescription(dto.getDescription());
        b.setTechnologies(dto.getTechnologies());
        b.setRecruitPeriod(dto.getRecruitPeriod());
        b.setStartPrice(dto.getStartPrice());
        b.setStatus(dto.getStatus());
        return repository.save(b);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Board close(Long id) {
        Board b = getById(id);
        b.setStatus("마감");
        return repository.save(b);
    }

    @Override
    public Board bid(Long id, Long amount) {
        Board b = getById(id);
        if (!"구인중".equals(b.getStatus())) {
            throw new IllegalArgumentException("이미 마감된 프로젝트입니다.");
        }
        b.setCurrentPrice(b.getCurrentPrice() + amount);
        return repository.save(b);
    }
}