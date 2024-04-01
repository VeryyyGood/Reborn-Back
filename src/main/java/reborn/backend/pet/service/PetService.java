package reborn.backend.pet.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reborn.backend.pet.converter.PetConverter;
import reborn.backend.pet.domain.Pet;
import reborn.backend.pet.dto.PetRequestDto.PetReqDto;
import reborn.backend.pet.repository.PetRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetService {

/*    private final PetRepository petRepository;

    @Transactional
    public Pet createPet(PetReqDto petReqDto) {
        Pet pet = PetConverter.toPet(petReqDto);

        petRepository.save(pet);

        return pet;
    }*/
}
