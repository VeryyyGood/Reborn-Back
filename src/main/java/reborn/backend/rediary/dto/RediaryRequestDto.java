package reborn.backend.rediary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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
        
        @Schema(description = "감정 일기 사진 첨부 여부(첨부1, 미첨부 0)")
        private int photoAttached;

        @Schema(description = "감정 일기 작성일", example = "2024-03-21", pattern = "yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime rediaryCreatedAt;

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

        @Schema(description = "감정 일기 작성일", example = "2024-03-21", pattern = "yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDateTime rediaryCreatedAt;

        @Schema(description = "감정 일기 사진 첨부 여부(첨부1, 미첨부 0)")
        private int photoAttached;

        @Schema(description = "감정 상태(SUNNY, CLOUDY, RAINY)")
        private String emotionStatus; // 감정 상태를 추가합니다.
    }

}
