package reborn.backend.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reborn.backend.board.domain.BoardType;

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
    }

}
