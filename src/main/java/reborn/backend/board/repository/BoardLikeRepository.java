package reborn.backend.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reborn.backend.board.domain.Board;
import reborn.backend.board.domain.BoardLike;
import reborn.backend.user.domain.User;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    // 게시판 좋아요 개수 조회
    Long countAllByBoard(Board board);

    // 사용자와 게시판에 대한 좋아요 정보 조회
    BoardLike findByUserAndBoard(User user, Board board);
}