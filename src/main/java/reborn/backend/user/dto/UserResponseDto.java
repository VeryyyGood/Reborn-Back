package reborn.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class UserResponseDto {
    @Schema(description = "UserInfoResDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfoResDto {

        @Schema(description = "가입시기")
        private LocalDateTime since;

        @Schema(description = "이메일")
        private String email;
    }
}
