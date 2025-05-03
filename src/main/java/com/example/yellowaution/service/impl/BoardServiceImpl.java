package com.example.yellowaution.service.impl;

import com.example.yellowaution.domain.Board;
import com.example.yellowaution.dto.BoardDto;
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
    public List<BoardDto> findAll() {
        return repository.findAll().stream()
                .map(b -> new BoardDto(
                        b.getId(),
                        b.getTitle(),
                        b.getDueDate(),
                        b.getDescription(),
                        b.getTechnologies(),
                        b.getRecruitPeriod(),
                        b.getStartPrice(),
                        b.getCurrentPrice(),
                        b.getStatus(),
                        b.getCreatedAt(),
                        b.getUser().getId(),
                        b.getUser().getUsername()
                ))
                .toList();
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

        // 작성자 일치 여부 확인
        Long loginUserId = dto.getUser().getId();  // 전달받은 dto.user.id
        if (!b.getUser().getId().equals(loginUserId)) {
            throw new SecurityException("작성자만 수정할 수 있습니다.");
        }

        // 수정 적용
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