package reborn.backend.board.converter;

import lombok.NoArgsConstructor;
import reborn.backend.board.domain.Board;
import reborn.backend.board.domain.Comment;
import reborn.backend.board.dto.CommentRequestDto;
import reborn.backend.board.dto.CommentResponseDto;
import reborn.backend.user.domain.User;

import java.util.List;

@NoArgsConstructor
public class CommentConverter {
    public static Comment saveComment(CommentRequestDto.CommentDto comment, Board board, User user){
        return Comment.builder()
                .commentWriter(user.getUsername())
                .commentContent(comment.getCommentContent())
                .board(board)
                .build();
    }

    public static CommentResponseDto.SimpleCommentDto simpleCommentDto(Comment comment) {
        return CommentResponseDto.SimpleCommentDto.builder()
                .id(comment.getId())
                .commentWriter(comment.getCommentWriter())
                .commentContent(comment.getCommentContent())
                .commentCreatedAt(comment.getCreatedAt())
                .build();
    }

    public static CommentResponseDto.CommentListResDto commentListResDto(List<Comment> comments){
        List<CommentResponseDto.SimpleCommentDto> commentDtos
                = comments.stream().map(CommentConverter::simpleCommentDto).toList();

        return CommentResponseDto.CommentListResDto.builder()
                .commentList(commentDtos)
                .build();
    }
}