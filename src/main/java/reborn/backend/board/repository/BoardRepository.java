package reborn.backend.board.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reborn.backend.board.domain.Board;
import reborn.backend.board.domain.BoardType;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b WHERE (:boardType = 'ALL' OR b.boardType = :boardType) ORDER BY " + // type이 'ALL'이면 모든 게시판 보이게
            "CASE WHEN :way = 'like' THEN b.likeCount END DESC, " +
            "CASE WHEN :way = 'like' THEN b.createdAt END DESC, " + // 좋아요 수가 같을 때 시간 내림차순 정렬
            "CASE WHEN :way = 'time' THEN b.createdAt END DESC")
    Slice<Board> findByBoardTypeAndWay(@Param("boardType") BoardType boardType, @Param("way") String way, Pageable pageable);

    @Query("SELECT b FROM Board b JOIN b.bookmarkList bm WHERE bm.user.id = :userId AND (:boardType = 'ALL' OR b.boardType = :boardType) ORDER BY " +
            "CASE WHEN :way = 'like' THEN b.likeCount END DESC, " +
            "CASE WHEN :way = 'like' THEN b.createdAt END DESC, " +
            "CASE WHEN :way = 'time' THEN b.createdAt END DESC")
    Slice<Board> findBookmarkByUserIdAndBoardTypeAndWay(@Param("userId") Long userId, @Param("boardType") BoardType boardType, @Param("way") String way, Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.user.id = :userId AND (:boardType = 'ALL' OR b.boardType = :boardType) ORDER BY " +
            "CASE WHEN :way = 'like' THEN b.likeCount END DESC, " +
            "CASE WHEN :way = 'like' THEN b.createdAt END DESC, " +
            "CASE WHEN :way = 'time' THEN b.createdAt END DESC")
    Slice<Board> findMyByUserIdAndBoardTypeAndWay(@Param("userId") Long userId, @Param("boardType") BoardType boardType, @Param("way") String way, Pageable pageable);

}