package reborn.backend.rediary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
public class RediaryResponseDto {

    @Schema(description = "RediaryListDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RediaryListResDto{
        @Schema(description = "감정 일기 리스트")
        private List<DetailRediaryDto> rediaryList;
    }

    @Schema(description = "DetailRediaryDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailRediaryDto{
        @Schema(description = "감정 일기 id")
        private Long rediaryId;

        @Schema(description = "감정 일기 작성자")
        private String rediaryWriter;

        @Schema(description = "감정 일기 제목")
        private String rediaryTitle;

        @Schema(description = "감정 일기 내용")
        private String rediaryContents;

        @Schema(description = "감정 일기 작성일", example = "2024-03-21", pattern = "yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate rediaryCreatedAt;

        @Schema(description = "선택한 감정 상태(SUNNY, CLOUDY, RAINY)")
        private String pickEmotion; // 감정 상태를 추가합니다.

        @Schema(description = "결과로 나온 감정 상태(RED, YELLOW, BLUE)")
        private String resultEmotion;
    }

    @Schema(description = "EmotionPercentageRediaryDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmotionPercentageRediaryDto{
        @Schema(description = "작업 성공 여부")
        private boolean success;

        @Schema(description = "선택한 감정의 오늘 중 비율")
        private double emotionPercentage;
    }
}
