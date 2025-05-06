package com.example.yellowaution.repository;

import com.example.yellowaution.domain.Board;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * BoardRepository 인터페이스는 Board 엔티티에 대한
 * CRUD 및 커스텀 업데이트 기능을 제공하는 Spring Data JPA 리포지토리입니다.
 *
 * <p>기본 CRUD 기능:
 * - findById, findAll, save, delete 등 JpaRepository에서 제공하는 메서드 사용 가능
 *
 * <p>커스텀 메서드:
 * - markExpired(): 마감 기한이 지난 게시물(status='구인중')을 한 번에 '마감' 처리
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    /**
     * 마감 기한(now) 이전에 등록된, 아직 '구인중' 상태인 게시물을
     * 모두 '마감' 상태로 변경하는 메서드입니다.
     *
     * <p>동작:
     * <ol>
     *   <li>파라미터 now (현재 시각)보다 dueDate가 이전이면서</li>
     *   <li>현재 상태(status)가 '구인중'인 Board 엔티티를 찾고</li>
     *   <li>JPQL UPDATE 문을 통해 status 필드를 '마감'으로 일괄 변경</li>
     * </ol>
     *
     * <p>리턴값:
     * - 변경된 행(row)의 개수(int)</p>
     *
     * <p>사용 예시 (Service 레이어):
     * <pre>
     *   int updatedCount = boardRepository.markExpired(LocalDateTime.now());
     *   log.info("만료 처리된 게시물 수: {}", updatedCount);
     * </pre>
     * </p>
     *
     * @param now    현재 시각 (LocalDateTime.now() 등)
     * @return       상태가 변경된 게시물 수
     */

    /*이 부분만 JQuery로 구현함(김민경) 한 번 사용해보기용 !!*/
    @Modifying  // UPDATE, DELETE 같은 DML 쿼리 실행 시 필요
    @Query("UPDATE Board b " +
            "SET b.status = '마감' " +
            "WHERE b.dueDate <= :now " +
            "  AND b.status = '구인중'")
    int markExpired(@Param("now") LocalDateTime now);

}
