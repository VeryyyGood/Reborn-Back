package reborn.backend.reborn_15._3_reveal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class RevealResponseDto {

    @Schema(description = "RevealListDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RevealListResDto {
        @Schema(description = "나의 감정 들여다보기 리스트")
        private List<SimpleRevealDto> revealList;
    }

    @Schema(description = "SimpleRevealDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SimpleRevealDto {
        @Schema(description = "선택한 감정 상태(SUNNY, CLOUDY, RAINY)")
        private String pickEmotion;

        @Schema(description = "결과로 나온 감정 상태(RED, YELLOW, BLUE)")
        private String resultEmotion;

        @Schema(description = "나의 감정 들여다보기 내용")
        private String diaryContent;

        @Schema(description = "나의 감정 들여다보기 작성일")
        private Integer date;
    }

    @Schema(description = "DetailRevealDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailRevealDto {
        @Schema(description = "나의 감정 들여다보기 id")
        private Long id;

        @Schema(description = "나의 감정 들여다보기 내용")
        private String diaryContent;

        @Schema(description = "나의 감정 들여다보기 작성일")
        private Integer date;

        @Schema(description = "선택한 감정 상태(SUNNY, CLOUDY, RAINY)")
        private String pickEmotion;

        @Schema(description = "결과로 나온 감정 상태(RED, YELLOW, BLUE)")
        private String resultEmotion;
    }
}
