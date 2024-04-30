package reborn.backend.comment.converter;

import lombok.NoArgsConstructor;
import reborn.backend.board.domain.Board;
import reborn.backend.comment.domain.Comment;
import reborn.backend.comment.dto.CommentRequestDto;
import reborn.backend.comment.dto.CommentResponseDto;
import reborn.backend.user.domain.User;

import java.util.List;

@NoArgsConstructor
public class CommentConverter {
    public static Comment saveComment(CommentRequestDto.CommentDto comment, Board board, User user){
        return Comment.builder()
                .commentWriter(user.getNickname())
                .commentContent(comment.getCommentContent())
                .board(board)
                .build();
    }

    public static CommentResponseDto.CommentResDto commentResDto(Comment comment) {
        return CommentResponseDto.CommentResDto.builder()
                .id(comment.getId())
                .commentWriter(comment.getCommentWriter())
                .commentContent(comment.getCommentContent())
                .commentCreatedAt(comment.getCreatedAt())
                .build();
    }

    public static CommentResponseDto.CommentListResDto commentListResDto(List<Comment> comments){
        List<CommentResponseDto.CommentResDto> commentDtos
                = comments.stream().map(CommentConverter::commentResDto).toList();

        return CommentResponseDto.CommentListResDto.builder()
                .commentList(commentDtos)
                .build();
    }
}
