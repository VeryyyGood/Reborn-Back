package reborn.backend.pet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
public class PetResponseDto {

    @Schema(description = "PetListDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PetListDto{
        @Schema(description = "반려동물 리스트")
        private List<DetailPetDto> petList;
    }


    @Schema(description = "DetailPetDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailPetDto{
        @Schema(description = "반려동물 id")
        private Long petId;

        @Schema(description = "반려동물 이름")
        private String petName;

        @Schema(description = "반려동물 기일")
        private LocalDate anniversary;

        @Schema(description = "반려동물 종류(DOG, CAT)")
        private String petType;

        @Schema(description = "반료동물 종")
        private String detailPetType;

        @Schema(description = "반려동물 색깔(BLACK, BROWN, LIGHTBROWN, GRAY, WHITE")
        private String petColor;

        @Schema(description = "반려동물 사진 url?")
        private String petImage;
    }
}
