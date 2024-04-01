package reborn.backend.pet.converter;

import lombok.NoArgsConstructor;
import reborn.backend.pet.domain.Pet;
import reborn.backend.pet.domain.PetColor;
import reborn.backend.pet.domain.PetType;
import reborn.backend.pet.dto.PetRequestDto.PetReqDto;

@NoArgsConstructor
public class PetConverter {

    public static Pet toPet(PetReqDto pet) {
        return Pet.builder()
                .petName(pet.getPetName())
                .anniversary(pet.getAnniversary())
                .petType(PetType.valueOf(pet.getPetType()))
                .petColor(PetColor.valueOf(pet.getPetColor()))
                .build();
    }
}
