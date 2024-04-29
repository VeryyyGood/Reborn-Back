package reborn.backend.pet.converter;

import lombok.NoArgsConstructor;
import reborn.backend.pet.domain.Pet;
import reborn.backend.pet.domain.PetColor;
import reborn.backend.pet.domain.PetType;
import reborn.backend.pet.dto.PetRequestDto.PetReqDto;
import reborn.backend.pet.dto.PetResponseDto.DetailPetDto;
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
}
