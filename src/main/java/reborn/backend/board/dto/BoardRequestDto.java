package reborn.backend.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reborn.backend.board.domain.BoardType;
import reborn.backend.user.domain.User;

import java.time.LocalDateTime;

@NoArgsConstructor
public class BoardRequestDto {
    @Schema(description = "BoardReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BoardReqDto {

        @Schema(description = "게시판 종류")
        private BoardType boardType;

        @Schema(description = "게시판 내용")
        private String boardContent;

        @Schema(description = "게시판 이미지 첨부 유무")
        private Integer imageAttached;

        @Schema(description = "게시판 이미지")
        private String boardImage;
    }

}
