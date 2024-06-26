package reborn.backend.pet.converter;

import lombok.NoArgsConstructor;
import reborn.backend.pet.domain.Pet;
import reborn.backend.global.entity.PetColor;
import reborn.backend.global.entity.PetType;
import reborn.backend.pet.dto.PetRequestDto.PetReqDto;
import reborn.backend.pet.dto.PetResponseDto.ByePetDto;
import reborn.backend.pet.dto.PetResponseDto.DetailPetDto;
import reborn.backend.pet.dto.PetResponseDto.PetNameDto;
import reborn.backend.pet.dto.PetResponseDto.SimplePetDto;
import reborn.backend.user.domain.User;

@NoArgsConstructor
public class PetConverter {

    public static Pet toPet(PetReqDto pet, User user, Integer date) {
        return Pet.builder()
                .user(user)
                .petName(pet.getPetName())
                .rebornDate(date)
                .anniversary(pet.getAnniversary())
                .petType(PetType.valueOf(pet.getPetType()))
                .detailPetType(pet.getDetailPetType())
                .petColor(PetColor.valueOf(pet.getPetColor()))
                .build();
    }

    public static SimplePetDto toSimplePetDto(Pet pet) {
        return SimplePetDto.builder()
                .petId(pet.getId())
                .petName(pet.getPetName())
                .anniversary(pet.getAnniversary())
                .build();
    }

    public static DetailPetDto toDetailPetDto(Pet pet) {
        return DetailPetDto.builder()
                .petName(pet.getPetName())
                .anniversary(pet.getAnniversary())
                .petType(String.valueOf(pet.getPetType()))
                .detailPetType(pet.getDetailPetType())
                .petColor(String.valueOf(pet.getPetColor()))
                .build();
    }

    public static ByePetDto toByePetDto(Pet pet) {
        return ByePetDto.builder()
                .petType(String.valueOf(pet.getPetType()))
                .rebornDate(pet.getRebornDate())
                .progressState(pet.getProgressState())
                .petName(String.valueOf(pet.getPetName()))
                .build();
    }

    public static PetNameDto toPetNameDto(Pet pet) {
        return PetNameDto.builder()
                .petName(pet.getPetName())
                .build();
    }
}
