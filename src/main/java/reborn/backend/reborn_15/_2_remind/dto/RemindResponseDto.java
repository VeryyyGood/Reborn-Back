package reborn.backend.reborn_15._2_remind.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class RemindResponseDto {

    @Schema(description = "RevealListDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RemindListResDto {
        @Schema(description = "충분한 대화 나누기 리스트")
        private List<SimpleRemindDto> remindList;
    }

    @Schema(description = "SimpleRemindDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SimpleRemindDto {
        @Schema(description = "질문 내용")
        private String question;

        @Schema(description = "질문 답변 내용")
        private String answer;

        @Schema(description = "충분한 대화 나누기 작성일")
        private Integer date;

        @Schema(description = "반려동물 이름")
        private String petName;

        @Schema(description = "반려동물 종류(DOG, CAT)")
        private String petType;
    }

    @Schema(description = "DetailRemindDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailRemindDto {
        @Schema(description = "충분한 대화 나누기 id")
        private Long id;

        @Schema(description = "질문 내용")
        private String question;

        @Schema(description = "질문 답변 내용")
        private String answer;

        @Schema(description = "충분한 대화 나누기 작성일")
        private Integer date;
    }
}
