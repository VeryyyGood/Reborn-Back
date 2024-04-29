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
    }

}
