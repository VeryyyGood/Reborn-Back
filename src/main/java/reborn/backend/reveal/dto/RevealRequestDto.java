package reborn.backend.reveal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
public class RevealRequestDto {

    @Schema(description = "RevealReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RevealReqDto {

        @Schema(description = "나의 감정 들여다보기 내용")
        private String revealContents;

        @Schema(description = "나의 감정 들여다보기 작성일")
        private Integer revealCreatedAt;

        @Schema(description = "선택한 감정 상태(SUNNY, CLOUDY, RAINY)")
        private String pickEmotion; // 감정 상태를 추가합니다.

        @Schema(description = "결과로 나온 감정 상태(RED, YELLOW, BLUE)")
        private String resultEmotion;
    }

    @Schema(description = "DetailRevealReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailRevealReqDto {
        @Schema(description = "나의 감정 들여다보기 id")
        private Long revealId;

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
