package reborn.backend.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentRequestDto {
    @Schema(description = "CommentDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CommentDto {
        @Schema(description = "댓글 내용")
        private String commentContent;
    }
}
