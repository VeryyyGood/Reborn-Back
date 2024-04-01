package reborn.backend.pet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
public class PetRequestDto {

    @Schema(description = "PetReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PetReqDto {
        @Schema(description = "반려동물 이름")
        private String petName;

        @Schema(description = "반려동물 기일")
        private LocalDate anniversary;

        @Schema(description = "반려동물 종류(DOG, CAT)")
        private String petType;

        @Schema(description = "반려동물 색깔(BLACK, BROWN, LIGHTBROWN, GRAY, WHITE")
        private String petColor;
    }

    @Schema(description = "DetailPetReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailPetReqDto {
        @Schema(description = "반려동물 id")
        private Long petId;

        @Schema(description = "반려동물 이름")
        private String petName;

        @Schema(description = "반려동물 기일")
        private LocalDate anniversary;

        @Schema(description = "반려동물 종류(DOG, CAT)")
        private String petType;

        @Schema(description = "반려동물 색깔(BLACK, BROWN, LIGHTBROWN, GRAY, WHITE")
        private String petColor;
    }
}
