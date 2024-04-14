package reborn.backend.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
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
        @NotBlank
        private BoardType boardType;

        @Schema(description = "게시판 내용")
        private String boardContent;

        @Schema(description = "게시판 이미지 첨부 유무")
        private Integer imageAttached;

        /*@Schema(description = "게시판 이미지")
        MultipartFile boardImage;*/
    }

}
