package reborn.backend.reborn_15._5_reborn.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._5_reborn.converter.RebornConverter;
import reborn.backend.reborn_15._5_reborn.domain.Reborn;
import reborn.backend.reborn_15._5_reborn.domain.RebornType;
import reborn.backend.reborn_15._5_reborn.dto.RebornRequestDto.ContentRebornReqDto;
import reborn.backend.reborn_15._5_reborn.dto.RebornRequestDto.RebornRebornReqDto;
import reborn.backend.reborn_15._5_reborn.repository.RebornRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RebornService {

    private final RebornRepository rebornRepository;

    @Transactional
    public Reborn createReborn(Pet pet) {
        Optional<Reborn> latestReborn = rebornRepository.findTopByPetOrderByDateDesc(pet);

        Integer newDate = latestReborn.map(r -> r.getDate() + 1).orElse(15);

        Reborn reborn = RebornConverter.toReborn(pet, newDate);

        rebornRepository.save(reborn);

        pet.setProgressState("INTRO");

        return reborn;
    }

    @Transactional
    public Reborn findByPetAndDateLessThanSorted(Pet pet, Integer date) {
        return rebornRepository.findByPetAndDateLessThan(pet, date);
    }

    @Transactional
    public void introReborn(Integer date, Pet pet) {
        Reborn reborn = rebornRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REBORN_NOT_FOUND));

        pet.setProgressState("PAT");
    }

    @Transactional
    public void patReborn(Integer date, Pet pet) {
        Reborn reborn = rebornRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REBORN_NOT_FOUND));

        reborn.setPat(true);

        pet.setProgressState("FEED");

        rebornRepository.save(reborn);
    }

    @Transactional
    public void feedReborn(Integer date, Pet pet) {
        Reborn reborn = rebornRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REBORN_NOT_FOUND));

        reborn.setFeed(true);

        pet.setProgressState("WASH");

        rebornRepository.save(reborn);
    }

    @Transactional
    public void washReborn(Integer date, Pet pet) {
        Reborn reborn = rebornRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REBORN_NOT_FOUND));

        reborn.setWash(true);

        pet.setProgressState("CLOTHES");

        rebornRepository.save(reborn);
    }

    @Transactional
    public void clotheReborn(Integer date, Pet pet) {
        Reborn reborn = rebornRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REBORN_NOT_FOUND));

        reborn.setClothe(true);

        pet.setProgressState("LETTER");

        rebornRepository.save(reborn);
    }

    @Transactional
    public void writeReborn(Integer date, ContentRebornReqDto rebornReqDto, Pet pet) {
        Reborn reborn = rebornRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REBORN_NOT_FOUND));

        reborn.setRebornContent(rebornReqDto.getRebornContent());

        pet.setProgressState("SETREBORN");

        rebornRepository.save(reborn);
    }

    @Transactional
    public void outroReborn(Integer date, Pet pet) {
        Reborn reborn = rebornRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REBORN_NOT_FOUND));

        pet.setProgressState("OUTRO");
    }

    @Transactional
    public void setReborn(Integer date, RebornRebornReqDto rebornReqDto, Pet pet) {
        Reborn reborn = rebornRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REBORN_NOT_FOUND));

        reborn.setRebornType(RebornType.valueOf(rebornReqDto.getRebornType()));

        rebornRepository.save(reborn);
    }

    @Transactional
    public Reborn findById(Long id) {
        return rebornRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REBORN_NOT_FOUND));
    }
}
