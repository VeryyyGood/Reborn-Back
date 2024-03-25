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
        private String rediaryContents;

        @Schema(description = "감정 상태(SUNNY, CLOUDY, RAINY)")
        private String emotionStatus; // 감정 상태를 추가합니다.
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
        private String rediaryContents;

        @Schema(description = "감정 상태(SUNNY, CLOUDY, RAINY)")
        private String emotionStatus; // 감정 상태를 추가합니다.
    }

}
