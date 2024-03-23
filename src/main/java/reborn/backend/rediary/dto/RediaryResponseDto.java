package reborn.backend.rediary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import reborn.backend.rediary.domain.Rediary;
import reborn.backend.rediary.domain.Rediary.EmotionStatus;

import java.time.LocalDateTime;
import java.util.List;

public class RediaryResponseDto {

    @Schema(description = "SimpleRediaryDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimpleRediaryDto{
        @Schema(description = "감정 일기 id")
        private Long id;

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
        private EmotionStatus emotionStatus; // 감정 상태를 추가합니다.
    }

    @Schema(description = "RediaryListDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RediaryListResDto{
        @Schema(description = "감정 일기 리스트")
        private List<SimpleRediaryDto> rediaryList;
    }

    @Schema(description = "DetailRediaryDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailRediaryDto{
        @Schema(description = "감정 일기 id")
        private Long id;

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
        private EmotionStatus emotionStatus; // 감정 상태를 추가합니다.
    }
}
