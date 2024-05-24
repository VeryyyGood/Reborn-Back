package reborn.backend.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reborn.backend.board.domain.Board;
import reborn.backend.comment.converter.CommentConverter;
import reborn.backend.comment.domain.Comment;
import reborn.backend.comment.dto.CommentRequestDto;
import reborn.backend.comment.dto.CommentResponseDto;
import reborn.backend.board.service.BoardService;
import reborn.backend.comment.service.CommentService;
import reborn.backend.fcm.service.FcmService;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;

import java.io.IOException;
import java.util.List;

@Tag(name = "댓글", description = "게시판 댓글 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/board/comment")
public class CommentController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;
    private final FcmService fcmService;

    @Operation(summary = "댓글 작성 메서드", description = "글을 작성하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT_2011", description = "댓글 생성이 완료되었습니다.")
    })
    @PostMapping("/{board-id}/create") //ok
    public ApiResponse<Long> create(
            @PathVariable(name = "board-id") Long boardId,
            @RequestBody CommentRequestDto.CommentDto commentDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) throws IOException {
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Long commentId = commentService.createComment(boardId, commentDto, user);
        Board board = boardService.findById(boardId);

        String title = String.valueOf(board.getBoardType());
        String body = "새로운 댓글: " + commentDto.getCommentContent();
        fcmService.sendMessageTo(board.getUser().getDeviceToken(), title, body);
        return ApiResponse.onSuccess(SuccessCode.COMMENT_CREATED,commentId);
    }

    @Operation(summary = "댓글 삭제 메서드", description = "댓글을 삭제하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT_2001", description = "댓글 삭제가 완료되었습니다.")
    })
    @DeleteMapping("/delete/{comment-id}") //ok
    public ApiResponse<Boolean> delete(
            @PathVariable(name = "comment-id") Long commentId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        Boolean res = commentService.deleteComment(commentId, user);

        return ApiResponse.onSuccess(SuccessCode.COMMENT_DELETED,res);
    }

    @Operation(summary = "댓글 목록 조회 메서드", description = "댓글 목록을 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT_2011", description = "댓글 생성이 완료되었습니다.")
    })
    @GetMapping("/{board-id}/list") // ok
    public ApiResponse<CommentResponseDto.CommentListResDto> list(
            @PathVariable(name = "board-id") Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        List<Comment> comments = commentService.findAllByBoardId(id);

        return ApiResponse.onSuccess(SuccessCode.COMMENT_LIST_VIEW_SUCCESS, CommentConverter.commentListResDto(comments));
    }

}
