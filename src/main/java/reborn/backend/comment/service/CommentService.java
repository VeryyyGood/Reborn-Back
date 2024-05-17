package reborn.backend.comment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reborn.backend.board.domain.Board;
import reborn.backend.comment.domain.Comment;
import reborn.backend.comment.converter.CommentConverter;
import reborn.backend.board.repository.BoardRepository;
import reborn.backend.comment.repository.CommentRepository;
import reborn.backend.comment.dto.CommentRequestDto.CommentDto;
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
    public Long createComment(Long boardId, CommentDto commentReqDto, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.BOARD_NOT_FOUND));

        Comment comment = CommentConverter.saveComment(commentReqDto, board, user);
        commentRepository.save(comment);

        updateCommentCount(board);
        boardRepository.save(board);

        return comment.getId();
    }

    @Transactional
    public Boolean deleteComment(Long commentID, User user){

        Comment comment = commentRepository.findById(commentID)
                .orElseThrow(() -> GeneralException.of(ErrorCode.COMMENT_NOT_FOUND));

        Board board = comment.getBoard();

        log.info("CommmentWriter: " + comment.getCommentWriter());
        log.info("Nickname: " + user.getNickname());
        if(Objects.equals(comment.getCommentWriter(), user.getNickname())){
            commentRepository.delete(comment);
        }
        else return false;

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
