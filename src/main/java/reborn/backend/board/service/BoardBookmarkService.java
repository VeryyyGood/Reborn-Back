package reborn.backend.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reborn.backend.board.domain.Board;
import reborn.backend.board.domain.BoardBookmark;
import reborn.backend.board.domain.BoardLike;
import reborn.backend.board.repository.BoardBookmarkRepository;
import reborn.backend.board.repository.BoardRepository;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.user.domain.User;
import reborn.backend.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardBookmarkService {
    private final BoardBookmarkRepository boardBookmarkRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 북마크 생성
    @Transactional
    public Integer createBookmark(Board board, User user) {
        BoardBookmark existingBookmark = boardBookmarkRepository.findByUserAndBoard(user, board);

        if (existingBookmark != null) {
            // 이미 북마크를 눌렀다면 좋아요 취소
            boardBookmarkRepository.delete(existingBookmark);
            boardRepository.save(board);
            return 0;
        } else {
            // 북마크 누르기
            boardBookmarkRepository.save(BoardBookmark.builder().user(user).board(board).build());
        }
        return 1;
    }

    // 북마크 취소
    @Transactional
    public Integer cancelBookmark(Board board, User user) {
        BoardBookmark existingBookmark = boardBookmarkRepository.findByUserAndBoard(user, board);

        if (existingBookmark != null) {
            // 이미 북마크를 눌렀다면 좋아요 취소
            boardBookmarkRepository.delete(existingBookmark);
            boardRepository.save(board);
            return 0;

        } else { // 취소할 북마크가 없으면 그대로 반환
            return 0;
        }
    }

    public Boolean checkBookmark(Long boardId, User user){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.BOARD_NOT_FOUND));
        Long userId = user.getId();
        for (BoardBookmark boardBookmark : board.getBookmarkList()) {
            if (boardBookmark.getUser().getId().equals(userId)) {
                return true; // 사용자가 북마크를 누른 상태
            }
        }
        return false; // 사용자가 북마크를 누르지 않은 상태
    }

}