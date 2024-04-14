package reborn.backend.board.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import reborn.backend.board.domain.*;
import reborn.backend.board.repository.BoardBookmarkRepository;
import reborn.backend.board.repository.BoardLikeRepository;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.board.converter.BoardConverter;
import reborn.backend.board.repository.BoardRepository;
import reborn.backend.board.repository.CommentRepository;
import reborn.backend.board.dto.BoardRequestDto.BoardReqDto;
import reborn.backend.global.config.AmazonConfig;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.user.domain.User;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final AmazonS3 amazonS3;

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final BoardBookmarkRepository boardBookmarkRepository;
    private final BoardLikeRepository boardLikeRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

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

/*    @Transactional
    public Board createBoard(BoardReqDto boardReqDto, User user) {
        Board board = BoardConverter.saveBoard(boardReqDto, user);
        boardRepository.save(board);

        return board;
    }*/

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

    // 사진 첨부 로직 추가
    @Transactional
    public Board createBoard(BoardReqDto boardReqDto, String dirName, MultipartFile file, User user) throws IOException{

        Board board = BoardConverter.saveBoard(boardReqDto, user); // 게시판 내용 저장

        //MultipartFile file = boardReqDto.getBoardImage();
        String uploadFileUrl = null;

        if (file != null) {
            String contentType = file.getContentType();
            if (ObjectUtils.isEmpty(contentType)) { // 확장자명이 존재하지 않을 경우 취소 처리
                throw GeneralException.of(ErrorCode.INVALID_FILE_CONTENT_TYPE);
            }
            java.io.File uploadFile = convert(file)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

            String fileName = dirName + generateFileName(file);
            uploadFileUrl = putS3(uploadFile, fileName);
        }

        board.setBoardImage(uploadFileUrl); // 사진 url 저장
        boardRepository.save(board);

        return board;
    }

    private Optional<File> convert(MultipartFile file) throws IOException { // 파일로 변환 - 필요해..?
        File convertedFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + file.getOriginalFilename());
        file.transferTo(convertedFile);
        return Optional.of(convertedFile);
    }

    private String putS3(java.io.File uploadFile, String fileName) { // S3로 업로드
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private String generateFileName(MultipartFile file) { // 파일명 생성
        return UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
    }

    private String contentType(String fileName) {
        String[] arr = fileName.split("\\.");
        String extension = arr[arr.length - 1].toLowerCase(); // 확장자를 소문자로 변환하여 비교
        return switch (extension) {
            case "txt" -> "text/plain";
            case "png" -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";
            default -> "application/octet-stream"; // 기본적으로 binary로 처리
        };
    }


    /*public ResponseEntity<byte[]> download(String fileUrl) throws IOException { // 객체 다운  fileUrl : 폴더명/파일네임.파일확장자
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, fileUrl));
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(contentType(fileUrl));
        httpHeaders.setContentLength(bytes.length);
        String[] arr = fileUrl.split("/");
        String type = arr[arr.length - 1];
        String fileName = URLEncoder.encode(type, "UTF-8").replaceAll("\\+", "%20");
        httpHeaders.setContentDispositionFormData("attachment", fileName); // 파일이름 지정

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }*/
}
