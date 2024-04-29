package reborn.backend.reborn_15._5_reborn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RebornRequestDto {

    @Schema(description = "RebornReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RebornReqDto {
        @Schema(description = "반려동물에게 하는 작별인사")
        private String rebornContent;

        @Schema(description = "리본 타입(YELLOW, BLACK)")
        private String rebornType;
    }

    @Schema(description = "DetailRebornReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailRebornReqDto {
        @Schema(description = "나의 반려동물과 건강한 작별하기")
        private Long id;

        @Schema(description = "15일 컨텐츠 상 날짜")
        private Integer date;

        @Schema(description = "반려동물에게 하는 작별인사")
        private String rebornContent;

        @Schema(description = "리본 타입(YELLOW, BLACK)")
        private String rebornType;

        @Schema(description = "쓰다듬기 상태")
        private Boolean pat;

        @Schema(description = "밥주기 상태")
        private Boolean feed;

        @Schema(description = "씻겨주기 상태")
        private Boolean wash;

        @Schema(description = "털 빗겨주기 상태")
        private Boolean brush;
    }
}
