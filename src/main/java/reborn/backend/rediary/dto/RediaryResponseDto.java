package reborn.backend.rediary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

        @Schema(description = "감정 상태(SUNNY, CLOUDY, RAINY)")
        private String emotionStatus; // 감정 상태를 추가합니다.
    }
}
