package reborn.backend.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reborn.backend.board.domain.Board;
import reborn.backend.board.service.BoardBookmarkService;
import reborn.backend.board.service.BoardLikeService;
import reborn.backend.board.service.BoardService;
import reborn.backend.board.converter.BoardConverter;
import reborn.backend.board.dto.BoardResponseDto.BoardResDto;
import reborn.backend.board.dto.BoardResponseDto.BoardListResDto;
import reborn.backend.board.dto.BoardRequestDto.BoardReqDto;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;
import java.io.IOException;
import java.util.List;

@Tag(name = "게시판", description = "게시판 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final UserService userService;
    private final BoardService boardService;
    private final BoardLikeService boardLikeService;
    private final BoardBookmarkService boardBookmarkService;

    @Operation(summary = "게시판 만들기 메서드", description = "게시판을 만드는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_2011", description = "게시판 생성이 완료되었습니다.")
    })
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Boolean> create(
            @RequestPart(value = "board", required = false) MultipartFile file,
            @RequestPart("data") BoardReqDto boardReqDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) throws IOException {
        String dirName = "board/";
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Board board = boardService.createBoard(boardReqDto, dirName, file, user);

        return ApiResponse.onSuccess(SuccessCode.BOARD_CREATED, true);
    }

    @Operation(summary = "게시물 상세 조회 메서드", description = "게시물 상세 정보를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_2001", description = "게시판 상세 조회가 완료되었습니다.")
    })
    @GetMapping("/{board-id}") // ok
    public ApiResponse<BoardResDto> detail(
            @PathVariable(name = "board-id") Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Board board =  boardService.findById(id);

        return ApiResponse.onSuccess(SuccessCode.BOARD_DETAIL_VIEW_SUCCESS, BoardConverter.simpleBoardDto(board));
    }

    @Operation(summary = "게시판 수정 메서드", description = "게시판 내용을 수정하는 메서드입니다.") // 작성자만 수정 가능
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_2002", description = "게시판 수정이 완료되었습니다.")
    })
    @PostMapping("/{board-id}/update") //OK
    public ApiResponse<BoardResDto> update(
            @PathVariable(name = "board-id") Long id,
            @RequestBody BoardReqDto boardReqDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Board board = boardService.updateBoard(id, boardReqDto, user);

        return ApiResponse.onSuccess(SuccessCode.BOARD_UPDATED, BoardConverter.simpleBoardDto(board));
    }

    @Operation(summary = "게시판 삭제 메서드", description = "게시판을 삭제하는 메서드입니다.") // 작성자만 삭제 사능
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_2003", description = "게시판 삭제가 완료되었습니다.")
    })
    @DeleteMapping("/{board-id}/delete") // 엮여있는 것들 다같이 삭제 되어야 함!
    public ApiResponse<Boolean> delete(
            @PathVariable(name = "board-id") Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        boardService.deleteBoard(id, user);

        return ApiResponse.onSuccess(SuccessCode.BOARD_DELETED, true);
    }

    @Operation(summary = "전체 게시판 목록 정보 조회 메서드", description = "type, way에 따라 게시판 목록을 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_2004", description = "게시판 목록 조회가 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "type", description = "조회하고 싶은 게시물 타입, EMOTION: 감정, ACTIVITY: 봉사, CHAT: 잡담"),
            @Parameter(name = "way", description = "정렬 방식,  like: 좋아요순, time: 최신순")
    })
    @GetMapping("/list")
    public ApiResponse<BoardListResDto> searchAllBoardsByType(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(name = "type") String boardType,
            @RequestParam(name = "way") String way
    ){
        return searchBoardsByTypeAndWay(customUserDetails, boardType, way, false, false);
    }

    @Operation(summary = "북마크 한 전체 게시판 목록 정보 조회 메서드", description = "북마크 한 게시판 중 type, way에 따라 목록을 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_2005", description = "게시판 목록 조회가 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "type", description = "조회하고 싶은 게시물 타입, EMOTION: 감정, ACTIVITY: 봉사, CHAT: 잡담"),
            @Parameter(name = "way", description = "정렬 방식,  like: 좋아요순, time: 최신순")
    })
    @GetMapping("/list/bookmark")
    public ApiResponse<BoardListResDto> searchBookmarkBoardsByType(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(name = "type") String boardType,
            @RequestParam(name = "way") String way
    ){
        return searchBoardsByTypeAndWay(customUserDetails, boardType, way, true, false);
    }

    @Operation(summary = "내가 작성한 전체 게시판 목록 정보 조회 메서드", description = "내가 작성한 게시판 중 type, way에 따라 목록을 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_2005", description = "게시판 목록 조회가 완료되었습니다.")
    })
    @Parameters({
            @Parameter(name = "type", description = "조회하고 싶은 게시물 타입, EMOTION: 감정, ACTIVITY: 봉사, CHAT: 잡담"),
            @Parameter(name = "way", description = "정렬 방식,  like: 좋아요순, time: 최신순")
    })
    @GetMapping("/list/my")
    public ApiResponse<BoardListResDto> searchMyBoardsByType(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(name = "type") String boardType,
            @RequestParam(name = "way") String way
    ){
        return searchBoardsByTypeAndWay(customUserDetails, boardType, way, false, true);
    }

    private ApiResponse<BoardListResDto> searchBoardsByTypeAndWay(
            CustomUserDetails customUserDetails,
            String boardType,
            String way,
            boolean isBookmark,
            boolean isMine
    ) {
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        List<Board> bookmarkBoards = isBookmark ? boardService.findBookmarkedBoard(user) : boardService.findAll();
        List<Board> myBoards =  isMine ? boardService.findByUser(user) : bookmarkBoards; // isBookmark && isMine의 경우가 없기에 가능
        List<Board> filteredBoards = boardService.filterBoardsByType(myBoards, boardType);
        List<Board> sortedBoards = boardService.sortBoardsByWay(filteredBoards, way);

        return ApiResponse.onSuccess(SuccessCode.BOARD_LIST_VIEW_SUCCESS, BoardConverter.boardListResDto(sortedBoards));
    }

    @Operation(summary = "게시물 좋아요 확인 메서드", description = "사용자가 게시물 좋아요 누른 여부 확인하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_2006", description = "게시물 좋아요 눌렀는지 확인 완료되었습니다")
    })
    @GetMapping("/{board-id}/check-like")// ok
    public ApiResponse<Boolean> checkLike(
            @PathVariable(name = "board-id") Long boardId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        Boolean check = boardLikeService.checkLike(boardId, user);
        return ApiResponse.onSuccess(SuccessCode.BOARD_LIKE_CHECK_SUCCESS, check);
    }

    @Operation(summary = "게시물 북마크 확인 메서드", description = "사용자가 게시물 북마크 누른 여부 확인하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOARD_2007", description = "게시물 북마크 눌렀는지 확인 완료되었습니다")
    })
    @GetMapping("/{board-id}/check-bookmark")// ok
    public ApiResponse<Boolean> checkBookmark(
            @PathVariable(name = "board-id") Long boardId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        Boolean check = boardBookmarkService.checkBookmark(boardId, user);
        return ApiResponse.onSuccess(SuccessCode.BOARD_BOOKMARK_CHECK_SUCCESS, check);
    }
}
