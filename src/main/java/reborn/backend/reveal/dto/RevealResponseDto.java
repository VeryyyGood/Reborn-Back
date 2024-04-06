package reborn.backend.reveal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@NoArgsConstructor
public class RevealResponseDto {

    @Schema(description = "RevealListDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RevealListResDto{
        @Schema(description = "나의 감정 들여다보기 리스트")
        private List<DetailRevealDto> remindList;
    }

    @Schema(description = "DetailRevealDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailRevealDto {

        @Schema(description = "나의 감정 들여다보기 id")
        private Long revealId;

        @Schema(description = "나의 감정 들여다보기 작성자")
        private String revealWriter;

        @Schema(description = "나의 감정 들여다보기 내용")
        private String revealContents;

        @Schema(description = "나의 감정 들여다보기 작성일")
        private Integer revealCreatedAt;

        @Schema(description = "선택한 감정 상태(SUNNY, CLOUDY, RAINY)")
        private String pickEmotion; // 감정 상태를 추가합니다.

        @Schema(description = "결과로 나온 감정 상태(RED, YELLOW, BLUE)")
        private String resultEmotion;
    }
}
