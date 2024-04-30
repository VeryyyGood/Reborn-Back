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
import reborn.backend.reborn_15._2_remind.domain.Remind;
import reborn.backend.reborn_15._2_remind.repository.RemindRepository;
import reborn.backend.reborn_15._3_reveal.domain.Reveal;
import reborn.backend.reborn_15._3_reveal.repository.RevealRepository;
import reborn.backend.reborn_15._4_remember.domain.Remember;
import reborn.backend.reborn_15._4_remember.repository.RememberRepository;
import reborn.backend.reborn_15._5_reborn.domain.Reborn;
import reborn.backend.reborn_15._5_reborn.repository.RebornRepository;
import reborn.backend.user.domain.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final RemindRepository remindRepository;
    private final RevealRepository revealRepository;
    private final RememberRepository rememberRepository;
    private final RebornRepository rebornRepository;

    @Transactional
    public Pet createPet(PetReqDto petReqDto, User user) {
        Integer newDate = 1;

        Pet pet = PetConverter.toPet(petReqDto, user, newDate);

        pet.setProgressState("RECONNECT");

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
    public void deletePet(Long id){
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.PET_NOT_FOUND));

        // 연관된 remind 엔티티 삭제
        List<Remind> reminds = pet.getRemindList();
        remindRepository.deleteAll(reminds);
        // 연관된 remind 엔티티 삭제
        List<Reveal> reveals = pet.getRevealList();
        revealRepository.deleteAll(reveals);
        // 연관된 remind 엔티티 삭제
        List<Remember> remembers = pet.getRememberList();
        rememberRepository.deleteAll(remembers);
        // 연관된 remind 엔티티 삭제
        List<Reborn> reborns = pet.getRebornList();
        rebornRepository.deleteAll(reborns);

        // 마지막으로 pet 삭제
        petRepository.delete(pet);
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
