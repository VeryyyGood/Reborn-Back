package reborn.backend.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reborn.backend.board.domain.Board;
import reborn.backend.board.service.BoardLikeService;
import reborn.backend.board.service.BoardService;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;

@Tag(name = "게시물 좋아요", description = "게시물 좋아요 관련 api 입니다.")
@RestController
@RequestMapping("/board/{board-id}/like")
@RequiredArgsConstructor
public class BoardLikeController {
    private final UserService userService;
    private final BoardService boardService;
    private final BoardLikeService boardLikeService;

    @Operation(summary = "게시물 좋아요 메서드", description = "게시물 좋아요하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LIKE_2001", description = "게시물 좋아요 성공")
    })
    @PostMapping("/create")
    public ApiResponse<Long> toggleLike(
            @PathVariable(name = "board-id") Long boardId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Board updatedBoard = boardLikeService.toggleLikeAndRetrieveCount(boardId, user);

        return ApiResponse.onSuccess(SuccessCode.BOARD_LIKE_SUCCESS, updatedBoard.getLikeCount());
    }

    @Operation(summary = "게시물 좋아요 취소 메서드", description = "게시물 좋아요를 취소하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LIKE_2002", description = "게시물 좋아요 취소 성공")
    })
    @DeleteMapping("/delete")
    public ApiResponse<Long> cancelLike(@PathVariable(name = "board-id") Long boardId,
                                        @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Board updatedBoard = boardLikeService.cancelLikeAndRetrieveCount(boardId, user);

        return ApiResponse.onSuccess(SuccessCode.BOARD_UNLIKE_SUCCESS, updatedBoard.getLikeCount());
    }

    @Operation(summary = "게시물 좋아요 개수 조회 메서드", description = "게시물 좋아요 개수 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LIKE_2003", description = "게시물 좋아요 개수 조회 성공")
    })
    @GetMapping("/count")
    public ApiResponse<Long> getLikeCount(@PathVariable(name = "board-id") Long boardId
    ) {
        Long likeCount = boardLikeService.getLikeCount(boardId);

        return ApiResponse.onSuccess(SuccessCode.BOARD_LIKE_COUNT_SUCCESS, likeCount);
    }
}
