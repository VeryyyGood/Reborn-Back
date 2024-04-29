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

}
