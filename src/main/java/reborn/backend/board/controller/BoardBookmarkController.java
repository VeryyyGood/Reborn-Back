package reborn.backend.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reborn.backend.board.domain.Board;
import reborn.backend.board.service.BoardBookmarkService;
import reborn.backend.board.service.BoardService;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;

@Tag(name = "게시판 북마크", description = "게시판 북마크 관련 api 입니다.")
@RestController
@RequestMapping("/board/{board-id}")
@RequiredArgsConstructor
public class BoardBookmarkController {
    private final UserService userService;
    private final BoardService boardService;
    private final BoardBookmarkService boardBookmarkService;

    @Operation(summary = "게시물 북마크 메서드", description = "게시물을 북마크하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOOKMARK_2001", description = "게시판 북마크 성공")
    })
    @PostMapping("/bookmark")
    public ApiResponse<Boolean> createBookmark(@PathVariable(name = "board-id") Long boardId,
                                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Board board = boardService.findById(boardId);

        if (board == null) {
            throw GeneralException.of(ErrorCode.BOARD_NOT_FOUND);
        }

        // 북마크 생성
        boardBookmarkService.createBookmark(board, user);
        return ApiResponse.onSuccess(SuccessCode.BOARD_BOOKMARK_SUCCESS, true);
    }

    @Operation(summary = "게시물 북마크 취소 메서드", description = "게시물의 북마크를 취소하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "BOOKMARK_2002", description = "게시판 북마크 취소 성공")
    })
    @DeleteMapping("/bookmark")
    public ApiResponse<Boolean> cancelBookmark(@PathVariable(name = "board-id") Long boardId,
                                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Board board = boardService.findById(boardId);

        if (board == null) {
            throw GeneralException.of(ErrorCode.BOARD_NOT_FOUND);
        }

        // 북마크 취소
        boardBookmarkService.cancelBookmark(board, user);

        return ApiResponse.onSuccess(SuccessCode.BOARD_UNBOOKMARK_SUCCESS, true);
    }

}