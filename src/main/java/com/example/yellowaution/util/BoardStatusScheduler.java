package com.example.yellowaution.util;

import com.example.yellowaution.repository.BoardRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class BoardStatusScheduler {

    private final BoardRepository boardRepository;

    public BoardStatusScheduler(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    @Transactional
    public void updateExpiredBoards() {
        int updatedCount = boardRepository.markExpired(LocalDate.now());
        // (선택) 변경된 건수 로그 출력
        if (updatedCount > 0) {
            System.out.println("[BoardStatusScheduler] 마감 처리된 게시글 수: " + updatedCount);
        }
    }
}
