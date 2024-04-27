package reborn.backend.reborn_15._2_remind.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RemindRequestDto {

    @Schema(description = "RemindReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RemindReqDto {

        @Schema(description = "질문 답변 내용")
        private String answer;

        @Schema(description = "충분한 대화 나누기 작성일")
        private Integer date;

        @Schema(description = "쓰다듬기 상태")
        private Boolean pat;

        @Schema(description = "밥주기 상태")
        private Boolean feed;

        @Schema(description = "산책하기 상태")
        private Boolean walk;

        @Schema(description = "간식주기 상태")
        private Boolean snack;
    }

    @Schema(description = "DetailRemindReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailRemindReqDto {
        @Schema(description = "충분한 대화 나누기 id")
        private Long id;

        @Schema(description = "질문 답변 내용")
        private String answer;

        @Schema(description = "충분한 대화 나누기 작성일")
        private Integer date;

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
