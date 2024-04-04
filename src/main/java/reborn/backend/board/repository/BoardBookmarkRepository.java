package reborn.backend.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reborn.backend.board.domain.Board;
import reborn.backend.board.domain.BoardBookmark;
import reborn.backend.user.domain.User;

@Repository
public interface BoardBookmarkRepository extends JpaRepository<BoardBookmark, Long> {

    // 사용자와 게시판에 대한 북마크 정보 조회
    BoardBookmark findByUserAndBoard(User user, Board board);
}