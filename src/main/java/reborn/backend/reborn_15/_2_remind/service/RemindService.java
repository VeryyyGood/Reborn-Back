package reborn.backend.reborn_15._2_remind.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.entity.PetType;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._2_remind.converter.RemindConverter;
import reborn.backend.reborn_15._2_remind.domain.Remind;
import reborn.backend.reborn_15._2_remind.dto.RemindRequestDto.RemindReqDto;
import reborn.backend.reborn_15._2_remind.repository.RemindRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemindService {

    private final RemindRepository remindRepository;

    @Transactional
    public Remind createRemind(Pet pet) {
        Optional<Remind> latestRemind = remindRepository.findTopByPetOrderByDateDesc(pet);

        Integer newDate = latestRemind.map(r -> r.getDate() + 1).orElse(2);

        Remind remind = RemindConverter.toRemind(pet, newDate);

        remindRepository.save(remind);

        pet.setProgressState("INTRO");

        return remind;
    }

    @Transactional
    public List<Remind> findAllByPetAndDateLessThanSortedByDate(Pet pet, Integer date) {
        return remindRepository.findAllByPetAndDateLessThanOrderByDateAsc(pet, date);
    }

    @Transactional
    public void introRemind(Integer date, Pet pet) {
        Remind remind = remindRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMIND_NOT_FOUND));

        pet.setProgressState("PAT");
    }

    @Transactional
    public void patRemind(Integer date, Pet pet) {
        Remind remind = remindRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMIND_NOT_FOUND));

        remind.setPat(true);

        pet.setProgressState("FEED");

        remindRepository.save(remind);
    }

    @Transactional
    public void feedRemind(Integer date, Pet pet) {
        Remind remind = remindRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMIND_NOT_FOUND));

        remind.setFeed(true);

        if ( pet.getPetType() == PetType.DOG ) {
            pet.setProgressState("WALK");
        } else {
            pet.setProgressState("PLAY");
        }

        remindRepository.save(remind);
    }

    @Transactional
    public void walkRemind(Integer date, Pet pet) {
        Remind remind = remindRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMIND_NOT_FOUND));

        remind.setWalk(true);

        pet.setProgressState("SNACK");

        remindRepository.save(remind);
    }

    @Transactional
    public void snackRemind(Integer date, Pet pet) {
        Remind remind = remindRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMIND_NOT_FOUND));

        remind.setSnack(true);

        pet.setProgressState("DIARY");

        remindRepository.save(remind);
    }

    @Transactional
    public void writeRemind(Integer date, RemindReqDto remindReqDto, Pet pet) {
        Remind remind = remindRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMIND_NOT_FOUND));
        remind.setQuestion(remindReqDto.getQuestion());
        remind.setAnswer(remindReqDto.getAnswer());

        remindRepository.save(remind);

        pet.setProgressState("FINISH");
    }

    @Transactional
    public Remind findById(Long id) {
        return remindRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMIND_NOT_FOUND));
    }
}
