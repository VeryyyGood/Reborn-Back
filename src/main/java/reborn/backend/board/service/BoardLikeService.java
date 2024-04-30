package reborn.backend.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reborn.backend.board.domain.Board;
import reborn.backend.board.domain.BoardLike;
import reborn.backend.board.repository.BoardLikeRepository;
import reborn.backend.board.repository.BoardRepository;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.user.domain.User;
import reborn.backend.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardLikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 좋아요 토글 및 좋아요 수 조회
    @Transactional
    public Board toggleLikeAndRetrieveCount(Long boardId, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.BOARD_NOT_FOUND));

        BoardLike existingLike = boardLikeRepository.findByUserAndBoard(user, board);

        if (existingLike != null) {
            // 이미 좋아요를 눌렀다면 에러 반환
            throw new GeneralException(ErrorCode.ALREADY_LIKED_BOARD);
        } else {
            // 좋아요를 누르지 않았다면, 좋아요 누르기
            boardLikeRepository.save(BoardLike.builder().user(user).board(board).build());
            // 좋아요 수 업데이트
            updateLikeCount(board);
        }

        return boardRepository.save(board);
    }

    // 좋아요 취소 및 좋아요 수 조회
    @Transactional
    public Board cancelLikeAndRetrieveCount(Long boardId, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.BOARD_NOT_FOUND));

        BoardLike existingLike = boardLikeRepository.findByUserAndBoard(user, board);

        if (existingLike != null) {
            // 이미 좋아요를 눌렀다면 좋아요 취소
            boardLikeRepository.delete(existingLike);
            // 좋아요 수 업데이트
            updateLikeCount(board);
            return boardRepository.save(board);

        } else { // 취소할 좋아요가 없으면 에러 반환
            throw new GeneralException(ErrorCode.LIKED_BOARD_NOT_FOUND);
        }
    }

    public String checkLike(Long boardId, User user){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.BOARD_NOT_FOUND));
        Long userId = user.getId();
        for (BoardLike like : board.getLikeList()) {
            if (like.getUser().getId().equals(userId)) {
                return "liked"; // 사용자가 좋아요를 누른 상태
            }
        }
        return "not liked"; // 사용자가 좋아요를 누르지 않은 상태
    }

    // 좋아요 수 조회
    public Long getLikeCount(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.BOARD_NOT_FOUND));

        return boardLikeRepository.countAllByBoard(board);
    }

    // 좋아요 수 업데이트
    private void updateLikeCount(Board board) {
        Long likeCount = boardLikeRepository.countAllByBoard(board);
        board.updateLikeCount(likeCount);
    }

}
