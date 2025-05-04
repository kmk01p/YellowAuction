package com.example.yellowaution.service.impl;

import com.example.yellowaution.domain.Bid;
import com.example.yellowaution.domain.Board;
import com.example.yellowaution.domain.User;
import com.example.yellowaution.dto.BoardDto;
import com.example.yellowaution.mapper.BoardMapper; // ✅ 추가
import com.example.yellowaution.repository.BidRepository;
import com.example.yellowaution.repository.BoardRepository;
import com.example.yellowaution.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {
    private final BoardRepository repository;
    private final BoardMapper boardMapper; // ✅ MyBatis 주입
    private final BidRepository bidRepository;

    @Override
    public List<BoardDto> findAll() {
        return repository.findAll().stream()
                .map(BoardDto::from)
                .toList();
    }

    @Override
    public List<BoardDto> searchByKeyword(String keyword) {
        var boards = boardMapper.searchByKeyword(keyword);
        return boards.stream().map(BoardDto::from).toList();
    }

    @Override
    public List<BoardDto> sortByCriteria(String sort) {
        var boards = boardMapper.sortByCriteria(sort);
        return boards.stream().map(BoardDto::from).toList();
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
        Long loginUserId = dto.getUser().getId();
        if (!b.getUser().getId().equals(loginUserId)) {
            throw new SecurityException("작성자만 수정할 수 있습니다.");
        }

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
    public Board bid(Long boardId, Long amount, HttpSession session) {
        Board board = getById(boardId);

        if (!"구인중".equals(board.getStatus())) {
            throw new IllegalArgumentException("이미 마감된 프로젝트입니다.");
        }

        // 세션에서 로그인 사용자 꺼내기
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new SecurityException("로그인한 사용자만 입찰할 수 있습니다.");
        }

        // Bid 객체 생성 및 저장
        Bid bid = new Bid();
        bid.setAmount(amount);
        bid.setBoard(board);
        bid.setUser(loginUser); // 세션에서 꺼낸 User
        bid.setCreatedAt(LocalDateTime.now());

        bidRepository.save(bid); // id는 자동 생성됨


        // 입찰가 반영
        board.setCurrentPrice(board.getCurrentPrice() + amount);
        return repository.save(board);
    }

}
