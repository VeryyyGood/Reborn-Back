package reborn.backend.pet.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.pet.converter.PetConverter;
import reborn.backend.pet.domain.Pet;
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
        Integer newDate = 1;

        Pet pet = PetConverter.toPet(petReqDto, user, newDate);

        petRepository.save(pet);
        return pet;
    }

    @Transactional
    public void updateDate(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.PET_NOT_FOUND));

        pet.setRebornDate(pet.getRebornDate() + 1);
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

    // 유저 가지고 pet id 가져오고 리턴

}
