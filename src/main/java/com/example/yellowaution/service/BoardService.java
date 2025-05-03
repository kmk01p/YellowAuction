package com.example.yellowaution.service;
import com.example.yellowaution.domain.Board;
import com.example.yellowaution.dto.BoardDto;

import java.util.List;

public interface BoardService {
    List<BoardDto> findAll();
    List<BoardDto> searchByKeyword(String keyword);
    List<BoardDto> sortByCriteria(String sort);
    Board getById(Long id);
    Board create(Board board);
    Board update(Long id, Board board);
    void delete(Long id);
    Board close(Long id);
    Board bid(Long id, Long amount);
}
