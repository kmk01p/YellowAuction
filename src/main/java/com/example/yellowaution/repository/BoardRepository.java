package com.example.yellowaution.repository;

import com.example.yellowaution.domain.Board;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Modifying
    @Query("UPDATE Board b SET b.status = '마감' WHERE b.dueDate <= :today AND b.status <> '마감'")
    int markExpired(@Param("today") LocalDate today);
}