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

    // 북마크 생성
    @Transactional
    public String createBookmark(Long boardId, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.BOARD_NOT_FOUND));

        BoardBookmark existingBookmark = boardBookmarkRepository.findByUserAndBoard(user, board);

        if (existingBookmark != null) {
            // 이미 북마크를 눌렀다면 에러 반환
            throw new GeneralException(ErrorCode.ALREADY_BOOKMARKED_BOARD);
        } else {
            // 북마크 누르지 않았다면, 북마크 생성
            boardBookmarkRepository.save(BoardBookmark.builder().user(user).board(board).build());
        }
        return "bookmark created";
    }

    // 북마크 취소
    @Transactional
    public String cancelBookmark(Long boardId, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.BOARD_NOT_FOUND));

        BoardBookmark existingBookmark = boardBookmarkRepository.findByUserAndBoard(user, board);

        if (existingBookmark != null) {
            // 이미 북마크를 눌렀다면 북마크 취소
            boardBookmarkRepository.delete(existingBookmark);
            boardRepository.save(board);
            return "bookmark canceled";

        } else { // 취소할 북마크가 없으면 에러 반환
            throw new GeneralException(ErrorCode.BOOKMARKED_BOARD_NOT_FOUND);
        }
    }

    public String checkBookmark(Long boardId, User user){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> GeneralException.of(ErrorCode.BOARD_NOT_FOUND));
        Long userId = user.getId();
        for (BoardBookmark boardBookmark : board.getBookmarkList()) {
            if (boardBookmark.getUser().getId().equals(userId)) {
                return "bookmarked";
            }
        }
        return "not bookmarked";
    }

}