package reborn.backend.reborn_15._3_reveal.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.entity.PickEmotion;
import reborn.backend.global.entity.ResultEmotion;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._3_reveal.converter.RevealConverter;
import reborn.backend.reborn_15._3_reveal.domain.Reveal;
import reborn.backend.reborn_15._3_reveal.dto.RevealRequestDto.RevealReqDto;
import reborn.backend.reborn_15._3_reveal.repository.RevealRepository;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class RevealService {

    private final RevealRepository revealRepository;

    @Transactional
    public Reveal createReveal(Pet pet) {
        Optional<Reveal> latestReveal = revealRepository.findTopByPetOrderByDateDesc(pet);

        Integer newDate = latestReveal.map(r -> r.getDate() + 1).orElse(7);

        Reveal reveal = RevealConverter.toReveal(pet, newDate);

        revealRepository.save(reveal);

        pet.setProgressState("INTRO");

        return reveal;
    }

    @Transactional
    public List<Reveal> findAllByPetAndDateLessThanSortedByDate(Pet pet, Integer date) {
        return revealRepository.findAllByPetAndDateLessThanOrderByDateDesc(pet, date);
    }

    @Transactional
    public boolean reviewCheckReveal(Pet pet, Integer date) {
        List<Reveal> revealList = revealRepository.findAllByPetAndDateLessThanOrderByDateDesc(pet, date);

        if( revealList.isEmpty() ) return false;
        return true;
    }

    @Transactional
    public void introReveal(Integer date, Pet pet) {
        Reveal reveal = revealRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));

        reveal.getPet().setProgressState("PAT");
    }

    @Transactional
    public void patReveal(Integer date, Pet pet) {
        Reveal reveal = revealRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));

        reveal.setPat(true);

        reveal.getPet().setProgressState("FEED");

        revealRepository.save(reveal);
    }

    @Transactional
    public void feedReveal(Integer date, Pet pet) {
        Reveal reveal = revealRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));

        reveal.setFeed(true);

        reveal.getPet().setProgressState("WALK");

        revealRepository.save(reveal);
    }

    @Transactional
    public void walkReveal(Integer date, Pet pet) {
        Reveal reveal = revealRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));

        reveal.setWalk(true);

        reveal.getPet().setProgressState("SNACK");

        revealRepository.save(reveal);
        }

    @Transactional
    public void snackReveal(Integer date, Pet pet) {
        Reveal reveal = revealRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));

        reveal.setSnack(true);

        reveal.getPet().setProgressState("EMOTION");

        revealRepository.save(reveal);
    }

    @Transactional
    public Reveal writeReveal(Integer date, RevealReqDto revealReqDto, Pet pet) {
        Reveal reveal = revealRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));

        reveal.setDiaryContent(revealReqDto.getDiaryContent());
        reveal.setPickEmotion(PickEmotion.valueOf(revealReqDto.getPickEmotion()));
        reveal.setResultEmotion(ResultEmotion.valueOf(revealReqDto.getResultEmotion()));

        revealRepository.save(reveal);

        reveal.getPet().setProgressState("FINISH");

        return reveal;
    }

    @Transactional
    public Reveal findById(Long id) {
        return revealRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));
    }

    @Transactional
    public Double calculateEmotionPercentage(Integer date, Pet pet) {
        Reveal reveal = revealRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));

        List<Reveal> allRevealsByDate = revealRepository.findAllByDate(reveal.getDate());

        long countSelectedEmotion = allRevealsByDate.stream()
                .filter(r -> r.getResultEmotion() == reveal.getResultEmotion())
                .count();

        long totalTodayReveals = allRevealsByDate.stream()
                .filter(r -> r.getResultEmotion() == ResultEmotion.RED ||
                        r.getResultEmotion() == ResultEmotion.YELLOW ||
                        r.getResultEmotion() == ResultEmotion.BLUE)
                .count();


        return (double) countSelectedEmotion / totalTodayReveals * 100;
    }
}
