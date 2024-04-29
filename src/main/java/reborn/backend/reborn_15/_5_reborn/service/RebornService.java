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
import reborn.backend.reborn_15._5_reborn.dto.RebornRequestDto.RebornReqDto;
import reborn.backend.reborn_15._5_reborn.repository.RebornRepository;

import java.util.List;
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

        return reborn;
    }

    @Transactional
    public List<Reborn> findAllByPetAndDateLessThanSortedByDate(Pet pet, Integer date) {
        return rebornRepository.findAllByPetAndDateLessThanOrderByDateDesc(pet, date);
    }

    @Transactional
    public void patReborn(Long id) {
        Reborn reborn = rebornRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REBORN_NOT_FOUND));

        reborn.setPat(true);

        rebornRepository.save(reborn);
    }

    @Transactional
    public void feedReborn(Long id) {
        Reborn reborn = rebornRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REBORN_NOT_FOUND));

        reborn.setFeed(true);

        rebornRepository.save(reborn);
    }

    @Transactional
    public void washReborn(Long id) {
        Reborn reborn = rebornRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REBORN_NOT_FOUND));

        reborn.setWash(true);

        rebornRepository.save(reborn);
    }

    @Transactional
    public void brushReborn(Long id) {
        Reborn reborn = rebornRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REBORN_NOT_FOUND));

        reborn.setBrush(true);

        rebornRepository.save(reborn);
    }

    @Transactional
    public void writeReborn(Long id, RebornReqDto rebornReqDto) {
        Reborn reborn = rebornRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REBORN_NOT_FOUND));

        reborn.setRebornContent(rebornReqDto.getRebornContent());
        reborn.setRebornType(RebornType.valueOf(rebornReqDto.getRebornType()));

        rebornRepository.save(reborn);
    }

    @Transactional
    public Reborn findById(Long id) {
        return rebornRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REBORN_NOT_FOUND));
    }
}
