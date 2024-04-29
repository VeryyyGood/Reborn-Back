package reborn.backend.reborn_15._4_remember.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RememberRequestDto {

    @Schema(description = "SimpleRememberReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SimpleRememberReqDto {

        @Schema(description = "일기 내용")
        private String content;

    }
}
