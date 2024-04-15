package reborn.backend.reborn_15._4_remember.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RememberRequestDto {

    @Schema(description = "RememberReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RememberReqDto {

        @Schema(description = "일기 내용")
        private String content;

        @Schema(description = "이미지 url")
        private String rememberImage;

        @Schema(description = "건강한 작별 준비하기 작성일")
        private Integer date;

        @Schema(description = "쓰다듬기 상태")
        private Boolean pat;

        @Schema(description = "밥주기 상태")
        private Boolean feed;

        @Schema(description = "산책하기 상태")
        private Boolean walk;

        @Schema(description = "간식주기 상태")
        private Boolean snack;

        @Schema(description = "청소 여부_1")
        private Boolean clean_1;

        @Schema(description = "청소 여부_2")
        private Boolean clean_2;

        @Schema(description = "청소 여부_3")
        private Boolean clean_3;

        @Schema(description = "청소 여부_4")
        private Boolean clean_4;

        @Schema(description = "청소 여부_5")
        private Boolean clean_5;

        @Schema(description = "청소 여부_6")
        private Boolean clean_6;
    }

    @Schema(description = "DetailRememberReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailRememberReqDto {

        @Schema(description = "건강한 작별 준비하기 id")
        private Long id;

        @Schema(description = "일기 내용")
        private String content;

        @Schema(description = "이미지 url")
        private String rememberImage;

        @Schema(description = "쓰다듬기 상태")
        private Boolean pat;

        @Schema(description = "밥주기 상태")
        private Boolean feed;

        @Schema(description = "산책하기 상태")
        private Boolean walk;

        @Schema(description = "간식주기 상태")
        private Boolean snack;

        @Schema(description = "청소 여부_1")
        private Boolean clean_1;

        @Schema(description = "청소 여부_2")
        private Boolean clean_2;

        @Schema(description = "청소 여부_3")
        private Boolean clean_3;

        @Schema(description = "청소 여부_4")
        private Boolean clean_4;

        @Schema(description = "청소 여부_5")
        private Boolean clean_5;

        @Schema(description = "청소 여부_6")
        private Boolean clean_6;
    }
}
