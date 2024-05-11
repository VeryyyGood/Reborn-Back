package reborn.backend.reborn_15._4_remember.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
public class RememberRequestDto {

    @Schema(description = "SimpleRememberReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SimpleRememberReqDto {
        @Schema(description = "일기 제목")
        private String title;

        @Schema(description = "일기 내용")
        private String content;

        @Schema(description = "사진 날짜")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate imageDate;

    }
}
