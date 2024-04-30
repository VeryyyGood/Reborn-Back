package reborn.backend.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reborn.backend.board.domain.Board;
import reborn.backend.comment.domain.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 게시판 댓글 개수 조회
    Long countAllByBoard(Board board);

    // 게시판에 맞는 댓글들 찾아서 내림차순 정렬
    List<Comment> findAllByBoardOrderByIdDesc(Board board);

}

