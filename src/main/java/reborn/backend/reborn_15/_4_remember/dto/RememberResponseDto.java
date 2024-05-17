package reborn.backend.reborn_15._4_remember.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class RememberResponseDto {

    @Schema(description = "RememberListDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RememberListResDto {
        @Schema(description = "건강한 작별 준비하기 리스트")
        private List<SimpleRememberDto> rememberList;
    }

    @Schema(description = "SimpleRememberDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SimpleRememberDto {
        @Schema(description = "건강한 작별 준비하기 id")
        private Long id;

        @Schema(description = "일기 제목")
        private String title;

        @Schema(description = "이미지 url")
        private String rememberImage;

        @Schema(description = "건강한 작별 준비하기 작성일")
        private Integer date;
    }

    @Schema(description = "DetailRememberDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailRememberDto {
        @Schema(description = "일기 내용")
        private String content;

        @Schema(description = "사진 날짜")
        private String imageDate;

        @Schema(description = "이미지 url")
        private String rememberImage;
    }
}
