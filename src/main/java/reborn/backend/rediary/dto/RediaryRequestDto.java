package reborn.backend.rediary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
public class RediaryRequestDto {

    @Schema(description = "RediaryReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RediaryReqDto {

        @Schema(description = "감정 일기 제목")
        private String rediaryTitle;

        @Schema(description = "감정 일기 내용")
        private String rediaryContent;

        @Schema(description = "선택한 감정 상태(SUNNY, CLOUDY, RAINY)")
        private String pickEmotion; // 감정 상태를 추가합니다.

        @Schema(description = "결과로 나온 감정 상태(RED, YELLOW, BLUE)")
        private String resultEmotion;
    }

    @Schema(description = "DetailRediaryReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailRediaryReqDto {
        @Schema(description = "감정 일기 id")
        private Long rediaryId;

        @Schema(description = "감정 일기 제목")
        private String rediaryTitle;

        @Schema(description = "감정 일기 내용")
        private String rediaryContent;

        @Schema(description = "선택한 감정 상태(SUNNY, CLOUDY, RAINY)")
        private String pickEmotion; // 감정 상태를 추가합니다.

        @Schema(description = "결과로 나온 감정 상태(RED, YELLOW, BLUE)")
        private String resultEmotion;
    }

}
