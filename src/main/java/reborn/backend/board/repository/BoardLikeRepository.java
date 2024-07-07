package reborn.backend.board.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reborn.backend.board.domain.Board;
import reborn.backend.board.domain.BoardLike;
import reborn.backend.user.domain.User;
import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    // 게시판 좋아요 개수 조회
    Long countAllByBoard(Board board);

    // 사용자와 게시판에 대한 좋아요 정보 조회
    // BoardLike findByUserAndBoard(User user, Board board); - 이전 로직
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT bl FROM BoardLike bl WHERE bl.user = :user AND bl.board = :board")
    Optional<BoardLike> findByUserAndBoardWithLock(@Param("user") User user, @Param("board") Board board);
}