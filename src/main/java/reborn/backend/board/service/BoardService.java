package reborn.backend.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reborn.backend.board.domain.*;
import reborn.backend.board.repository.BoardBookmarkRepository;
import reborn.backend.board.repository.BoardLikeRepository;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.board.converter.BoardConverter;
import reborn.backend.board.repository.BoardRepository;
import reborn.backend.board.repository.CommentRepository;
import reborn.backend.board.dto.BoardRequestDto.BoardReqDto;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.user.domain.User;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final BoardBookmarkRepository boardBookmarkRepository;
    private final BoardLikeRepository boardLikeRepository;

    @Transactional
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional
    public Board findById(Long id){
        return boardRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.BOARD_NOT_FOUND));
    }

    @Transactional
    public List<Board> findBookmarkedBoard(User user) {
        return user.getBoardBookmarks().stream().map(BoardBookmark::getBoard).toList();
    }

    @Transactional
    public Board createBoard(BoardReqDto boardReqDto, User user) {
        Board board = BoardConverter.saveBoard(boardReqDto, user);
        boardRepository.save(board);

        return board;
    }

    @Transactional
    public Board updateBoard(Long id, BoardReqDto boardReqDto, User user) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.BOARD_NOT_FOUND));

        if(Objects.equals(board.getBoardWriter(), user.getUsername())){
            // 업데이트할 내용 설정
            board.setBoardType(boardReqDto.getBoardType());
            board.setBoardWriter(user.getUsername());
            board.setLikeCount(board.getLikeCount());
            board.setBoardContent(boardReqDto.getBoardContent());
            board.setImageAttached(boardReqDto.getImageAttached());
            board.setBoardImage(boardReqDto.getBoardImage());
            boardRepository.save(board);

            return board;
        }
        return board;
    }

    @Transactional
    public void deleteBoard(Long id, User user){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.BOARD_NOT_FOUND));

        log.info("BoardWriter: " + board.getBoardWriter());
        log.info("Username: " + user.getUsername());

        if(Objects.equals(board.getBoardWriter(), user.getUsername())){

            // 연관된 댓글 엔티티들 삭제
            List<Comment> comments = board.getCommentList();
            commentRepository.deleteAll(comments);
            // 연관된 게시물 북마크 엔티티들 삭제
            List<BoardBookmark> bookmarks = board.getBookmarkList();
            boardBookmarkRepository.deleteAll(bookmarks);
            // 연관된 게시물 좋아요 엔티티들 삭제
            List<BoardLike> likes = board.getLikeList();
            boardLikeRepository.deleteAll(likes);

            // 과제 삭제
            boardRepository.deleteById(id);
        }
        else throw new GeneralException(ErrorCode.BAD_REQUEST);
    }

    public List<Board> filterBoardsByType(List<Board> boards, String boardType) {

        if (boardType.equals("ALL")) {
            return boards;
        } else if (BoardType.valueOf(boardType).equals(BoardType.EMOTION)) {
            return boards.stream()
                    .filter(board -> board.getBoardType() == BoardType.EMOTION)
                    .toList();
        } else if (BoardType.valueOf(boardType).equals(BoardType.ACTIVITY)) {
            return boards.stream()
                    .filter(board -> board.getBoardType() == BoardType.ACTIVITY)
                    .toList();
        }else if (BoardType.valueOf(boardType).equals(BoardType.PRODUCT)) {
            return boards.stream()
                    .filter(board -> board.getBoardType() == BoardType.PRODUCT)
                    .toList();
        }else if (BoardType.valueOf(boardType).equals(BoardType.CHAT)) {
            return boards.stream()
                    .filter(board -> board.getBoardType() == BoardType.CHAT)
                    .toList();
        } else {
            throw GeneralException.of(ErrorCode.BOARD_WRONG_TYPE);
        }
    }

    public List<Board> sortBoardsByWay(List<Board> boards, String way) {
        if (way.equals("like")) {
            return boards.stream()
                    .sorted(Comparator.comparingLong(Board::getLikeCount).reversed()) // 많은 순
                    .toList();
        } else if (way.equals("time")) {
            return boards.stream()
                    .sorted(Comparator.comparing(Board::getCreatedAt).reversed()) // 최신순
                    .toList();
        } else {
            throw GeneralException.of(ErrorCode.BOARD_WRONG_SORTING_WAY);
        }
    }

}
