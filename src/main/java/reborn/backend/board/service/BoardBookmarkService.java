package reborn.backend.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reborn.backend.board.domain.Board;
import reborn.backend.board.domain.BoardBookmark;
import reborn.backend.board.repository.BoardBookmarkRepository;
import reborn.backend.board.repository.BoardRepository;
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
    public Board createBookmark(Board board, User user) {
        BoardBookmark existingBookmark = boardBookmarkRepository.findByUserAndBoard(user, board);

        if (existingBookmark != null) {
            // 이미 북마크를 눌렀다면 좋아요 취소
            boardBookmarkRepository.delete(existingBookmark);
        } else {
            // 북마크 누르기
            boardBookmarkRepository.save(BoardBookmark.builder().user(user).board(board).build());
        }

        return boardRepository.save(board);
    }

    // 북마크 취소
    @Transactional
    public Board cancelBookmark(Board board, User user) {
        BoardBookmark existingBookmark = boardBookmarkRepository.findByUserAndBoard(user, board);

        if (existingBookmark != null) {
            // 이미 북마크를 눌렀다면 좋아요 취소
            boardBookmarkRepository.delete(existingBookmark);

            return boardRepository.save(board);

        } else { // 취소할 북마크가 없으면 그대로 반환
            return board;
        }
    }

}