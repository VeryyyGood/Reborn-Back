package reborn.backend.pet.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.pet.converter.PetConverter;
import reborn.backend.pet.domain.Pet;
import reborn.backend.pet.domain.PetColor;
import reborn.backend.pet.domain.PetType;
import reborn.backend.pet.dto.PetRequestDto;
import reborn.backend.pet.dto.PetRequestDto.PetReqDto;
import reborn.backend.pet.repository.PetRepository;
import reborn.backend.user.domain.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    @Transactional
    public Pet createPet(PetReqDto petReqDto, User user) {
        Pet pet = PetConverter.toPet(petReqDto, user);

        petRepository.save(pet);
        return pet;
    }

    @Transactional
    public Pet updatePet(Long id, PetRequestDto.DetailPetReqDto detailPetReqDto) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REDIARY_NOT_FOUND));

        pet.setPetName(detailPetReqDto.getPetName());
        pet.setAnniversary(detailPetReqDto.getAnniversary());
        pet.setPetType(PetType.valueOf(detailPetReqDto.getPetType()));
        pet.setDetailPetType(detailPetReqDto.getDetailPetType());
        pet.setPetColor(PetColor.valueOf(detailPetReqDto.getPetColor()));

        petRepository.save(pet);

        return pet;
    }

    @Transactional
    public List<Pet> findAllByUserSortedByCreatedAt(User user) {
        return petRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

    @Transactional
    public Pet findById(Long id){
        return petRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.PET_NOT_FOUND));
    }
}
