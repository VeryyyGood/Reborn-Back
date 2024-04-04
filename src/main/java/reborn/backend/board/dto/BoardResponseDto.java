package reborn.backend.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reborn.backend.board.domain.BoardType;

import java.util.List;

@NoArgsConstructor
public class BoardResponseDto {
    @Schema(description = "BoardReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BoardResDto {

        @Schema(description = "게시판 id")
        private Long id;

        @Schema(description = "게시판 종류")
        private BoardType boardType;

        @Schema(description = "게시판 작성자") // nickname? username?
        private String boardWriter;

        @Schema(description = "좋아요 수")
        private Long likeCount;

        @Schema(description = "게시판 내용")
        private String boardContent;

        @Schema(description = "게시판 이미지 첨부 유무")
        private Integer imageAttached;

        @Schema(description = "게시판 이미지")
        private String boardImage;
    }

    @Schema(description = "BoardListResDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BoardListResDto {

        @Schema(description = "게시물 리스트")
        private List<BoardResDto> boardList;

    }

}
