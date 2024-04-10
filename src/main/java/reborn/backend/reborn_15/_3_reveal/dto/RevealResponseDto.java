package reborn.backend.reborn_15._3_reveal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reborn.backend.pet.domain.Pet;

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
        private List<DetailRevealDto> revealList;
    }

    @Schema(description = "DetailRevealDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailRevealDto {
        @Schema(description = "나의 감정 들여다보기 id")
        private Long id;

        @Schema(description = "펫")
        private Pet pet;

        @Schema(description = "나의 감정 들여다보기 내용")
        private String diaryContent;

        @Schema(description = "나의 감정 들여다보기 작성일")
        private Integer date;

        @Schema(description = "선택한 감정 상태(SUNNY, CLOUDY, RAINY)")
        private String pickEmotion;

        @Schema(description = "결과로 나온 감정 상태(RED, YELLOW, BLUE)")
        private String resultEmotion;

        @Schema(description = "쓰다듬기 상태")
        private Boolean pat;

        @Schema(description = "밥주기 상태")
        private Boolean feed;

        @Schema(description = "산책하기 상태")
        private Boolean walk;

        @Schema(description = "간식주기 상태")
        private Boolean snack;
    }
}
