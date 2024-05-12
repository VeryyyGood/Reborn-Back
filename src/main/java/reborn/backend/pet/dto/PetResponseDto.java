package reborn.backend.pet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        private List<SimplePetDto> petList;
    }

    @Schema(description = "SimplePetDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimplePetDto{
        @Schema(description = "반려동물 id")
        private Long petId;

        @Schema(description = "반려동물 이름")
        private String petName;

        @Schema(description = "반려동물 기일")
        private String anniversary;
    }

    @Schema(description = "DetailPetDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailPetDto{
        @Schema(description = "반려동물 이름")
        private String petName;

        @Schema(description = "반려동물 기일")
        private String anniversary;

        @Schema(description = "반려동물 종류(DOG, CAT)")
        private String petType;

        @Schema(description = "반료동물 종")
        private String detailPetType;

        @Schema(description = "반려동물 색깔(BLACK, BROWN, YELLOWDARK, GRAY, WHITE")
        private String petColor;
    }


    @Schema(description = "ByePetDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ByePetDto{
        @Schema(description = "반려동물 종류(DOG, CAT)")
        private String petType;

        @Schema(description = "컨텐츠 상 진행중인 날짜")
        private Integer rebornDate;

        @Schema(description = "컨텐츠 진행 상태")
        private String progressState;

        @Schema(description = "반려동물 이름")
        private String petName;
    }

    @Schema(description = "PetNameDto")
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PetNameDto{
        @Schema(description = "반려동물 이름")
        private String petName;
    }
}
