package reborn.backend.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reborn.backend.board.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}

