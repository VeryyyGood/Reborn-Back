package reborn.backend.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reborn.backend.board.domain.Board;
import reborn.backend.board.domain.BoardType;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findListByBoardType(BoardType boardType);
}
