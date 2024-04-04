package reborn.backend.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reborn.backend.board.converter.CommentConverter;
import reborn.backend.board.domain.Board;
import reborn.backend.board.domain.Comment;
import reborn.backend.board.dto.CommentRequestDto;
import reborn.backend.board.dto.CommentResponseDto;
import reborn.backend.board.service.BoardService;
import reborn.backend.board.service.CommentService;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;

import java.util.List;

@Tag(name = "댓글", description = "게시판 댓글 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;

    @Operation(summary = "댓글 작성 메서드", description = "글을 작성하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT_2011", description = "댓글 생성이 완료되었습니다.")
    })
    @PostMapping("/create")
    public ApiResponse<Boolean> create(
            @RequestBody CommentRequestDto.CommentDto submissionDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        commentService.createComment(submissionDto, user);

        return ApiResponse.onSuccess(SuccessCode.COMMENT_CREATED,true);
    }

    @Operation(summary = "댓글 삭제 메서드", description = "댓글을 삭제하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT_2001", description = "댓글 삭제가 완료되었습니다.")
    })
    @DeleteMapping("/delete/{id}")
    public ApiResponse<Boolean> delete(
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        commentService.deleteComment(id, user);

        return ApiResponse.onSuccess(SuccessCode.COMMENT_DELETED,true);
    }

    // 댓글 LIST
    @Operation(summary = "댓글 작성 메서드", description = "글을 작성하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT_2011", description = "댓글 생성이 완료되었습니다.")
    })
    @GetMapping("/list/{board-id}")
    public ApiResponse<CommentResponseDto.CommentListResDto> list(
            @PathVariable(name = "board-id") Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        List<Comment> comments = commentService.findAllByBoardId(id);

        return ApiResponse.onSuccess(SuccessCode.COMMENT_CREATED, CommentConverter.commentListResDto(comments));
    }

    // 대댓글은 추후 추가

}
