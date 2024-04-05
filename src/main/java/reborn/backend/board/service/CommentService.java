package reborn.backend.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reborn.backend.board.domain.Board;
import reborn.backend.board.domain.Comment;
import reborn.backend.board.converter.CommentConverter;
import reborn.backend.board.repository.BoardRepository;
import reborn.backend.board.repository.CommentRepository;
import reborn.backend.board.dto.CommentRequestDto.CommentDto;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.user.domain.User;
import reborn.backend.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public Boolean createComment(Long boardId, CommentDto commentReqDto, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.BOARD_NOT_FOUND));

        Comment comment = CommentConverter.saveComment(commentReqDto, board, user);
        commentRepository.save(comment);

        updateCommentCount(board);
        boardRepository.save(board);

        return true;
    }

    @Transactional
    public Boolean deleteComment(Long boardId, Long commentID, User user){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.BOARD_NOT_FOUND));

        Comment comment = commentRepository.findById(commentID)
                .orElseThrow(() -> GeneralException.of(ErrorCode.COMMENT_NOT_FOUND));

        log.info("CommmentWriter: " + comment.getCommentWriter());
        log.info("Username: " + user.getUsername());
        if(Objects.equals(comment.getCommentWriter(), user.getUsername())){
            commentRepository.delete(comment);
        }

        updateCommentCount(board);
        boardRepository.save(board);

        return true;
    }

    public List<Comment> findAllByBoardId(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.COMMENT_NOT_FOUND));
        return commentRepository.findAllByBoardOrderByIdDesc(board);
    }

    // 댓글 수 조회
    public Long getCommentCount(Board board) {
        return commentRepository.countAllByBoard(board);
    }

    // 댓글 수 업데이트
    private void updateCommentCount(Board board) {
        Long commentCount = commentRepository.countAllByBoard(board);
        board.updateCommentCount(commentCount);
    }
}
