package com.example.yellowaution.util;

import com.example.yellowaution.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class BoardStatusScheduler {

    private final BoardRepository boardRepository;

    public BoardStatusScheduler(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    /**
     * 매분 0초(1분에 한 번)마다 만료 처리
     */
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    @Transactional
    public void updateExpiredBoards() {
        // LocalDateTime.now() 를 넘겨주도록 변경
        LocalDateTime now = LocalDateTime.now();
        int updatedCount = boardRepository.markExpired(now);
        if (updatedCount > 0) {
            System.out.println("[BoardStatusScheduler] 마감 처리된 게시글 수: " + updatedCount);
        }
    }
}
