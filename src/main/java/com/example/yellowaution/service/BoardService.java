package com.example.yellowaution.service;

import com.example.yellowaution.domain.Board;

import java.util.List;

public interface BoardService {
    List<Board> findAll();
    Board getById(Long id);
    Board create(Board board);
    Board update(Long id, Board board);
    void delete(Long id);
    Board close(Long id);
    Board bid(Long id, Long amount);
}
