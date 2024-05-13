package reborn.backend.reborn_15._5_reborn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class RebornResponseDto {

    @Schema(description = "SimpleRebornDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SimpleRebornDto {

        @Schema(description = "리본 타입(YELLOW, BLACK)")
        private String rebornType;

        @Schema(description = "반려동물 종류(CAT, DOG)")
        private String petType;

        @Schema(description = "반려동물 이름")
        private String petName;
    }

    @Schema(description = "DetailRebornDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailRebornDto {
        @Schema(description = "나의 반려동물과 건강한 작별하기")
        private Long id;

        @Schema(description = "15일 컨텐츠 상 날짜")
        private Integer date;

        @Schema(description = "반려동물에게 하는 작별인사")
        private String rebornContent;

        @Schema(description = "리본 타입(YELLOW, BLACK)")
        private String rebornType;
    }
}
