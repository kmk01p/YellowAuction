package com.example.yellowaution.repository;

import com.example.yellowaution.domain.Bid;
import com.example.yellowaution.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * BidRepository 인터페이스는 JPA를 통해 Bid 엔티티에 대한
 * 기본 CRUD 및 커스텀 조회 메서드를 제공합니다.
 *
 * <p>JpaRepository를 상속함으로써,
 * - 기본적인 findById, findAll, save, delete 등의 메서드를 사용할 수 있습니다.
 * - 도메인에 특화된 쿼리 메서드를 메서드 명만으로 선언하여 자동 구현됩니다.</p>
 */
public interface BidRepository extends JpaRepository<Bid, Long> {

    /**
     * 특정 게시물(Board)에 대한 가장 최근 입찰 한 건을 조회합니다.
     *
     * <p>SQL로는 다음과 유사한 쿼리를 수행합니다:
     * <pre>
     * SELECT * FROM bid
     * WHERE board_id = :board.id
     * ORDER BY created_at DESC
     * LIMIT 1
     * </pre>
     *
     * @param board 조회할 대상 Board 엔티티
     * @return Optional&lt;Bid&gt; — 최신 입찰이 존재하면 해당 Bid, 없으면 Optional.empty()
     */
    Optional<Bid> findFirstByBoardOrderByCreatedAtDesc(Board board);

    /**
     * 특정 게시물 ID에 해당하는 모든 입찰을 생성 시각(createdAt) 내림차순으로 조회합니다.
     *
     * <p>SQL로는 다음과 유사한 쿼리를 수행합니다:
     * <pre>
     * SELECT * FROM bid
     * WHERE board_id = :boardId
     * ORDER BY created_at DESC
     * </pre>
     *
     * @param boardId 조회할 대상 게시물의 식별자(ID)
     * @return List&lt;Bid&gt; — 내림차순 정렬된 입찰 목록 (최신 순)
     */
    List<Bid> findByBoardIdOrderByCreatedAtDesc(Long boardId);

}
