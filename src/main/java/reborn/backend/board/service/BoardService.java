package reborn.backend.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import reborn.backend.board.domain.*;
import reborn.backend.board.repository.BoardBookmarkRepository;
import reborn.backend.board.repository.BoardLikeRepository;
import reborn.backend.comment.domain.Comment;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.board.converter.BoardConverter;
import reborn.backend.board.repository.BoardRepository;
import reborn.backend.comment.repository.CommentRepository;
import reborn.backend.board.dto.BoardRequestDto.BoardReqDto;
import reborn.backend.global.entity.BoardType;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.global.s3.AmazonS3Manager;
import reborn.backend.user.domain.User;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final BoardBookmarkRepository boardBookmarkRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final AmazonS3Manager amazonS3Manager;

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
        return user.getBoardBookmarkList().stream().map(BoardBookmark::getBoard).toList();
    }

    @Transactional
    public List<Board> findByUser(User user){
        return user.getBoardList().stream().toList();
    }

    @Transactional
    public Board createBoard(BoardReqDto boardReqDto, String dirName, MultipartFile file, User user) throws IOException{

        Board board = BoardConverter.saveBoard(boardReqDto, user); // 게시판 내용 저장

        String uploadFileUrl = null;

        if (file != null && !file.isEmpty()) {
            String contentType = file.getContentType();
            if (ObjectUtils.isEmpty(contentType)) { // 확장자명이 존재하지 않을 경우 취소 처리
                throw GeneralException.of(ErrorCode.INVALID_FILE_CONTENT_TYPE);
            }
            java.io.File uploadFile = amazonS3Manager.convert(file)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

            String fileName = dirName + amazonS3Manager.generateFileName(file);
            uploadFileUrl = amazonS3Manager.putS3(uploadFile, fileName);
        }

        board.setBoardImage(uploadFileUrl); // 사진 url 저장
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

    //---------------------------------------------------------
    public List<Board> getBoardList(BoardType boardType, String way, int scrollPosition, int fetchSize) {
        Slice<Board> boardSlice = boardRepository.findByBoardTypeAndWay(boardType, way, PageRequest.of(scrollPosition, fetchSize));
        return boardSlice.getContent();
    }

    public List<Board> getBookmarkBoardList(User user, BoardType boardType, String way, int scrollPosition, int fetchSize) {
        Long userId = user.getId();
        Slice<Board> boardSlice = boardRepository.findBookmarkByUserIdAndBoardTypeAndWay(userId, boardType, way, PageRequest.of(scrollPosition, fetchSize));
        return boardSlice.getContent();
    }

    public List<Board> getMyBoardList(User user, BoardType boardType, String way, int scrollPosition, int fetchSize) {
        Long userId = user.getId();
        Slice<Board> boardSlice = boardRepository.findMyByUserIdAndBoardTypeAndWay(userId, boardType, way, PageRequest.of(scrollPosition, fetchSize));
        return boardSlice.getContent();
    }

/*  정렬 기존 로직
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
 */
    //---------------------------------------------------------

}
