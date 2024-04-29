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
import reborn.backend.reborn_15._3_reveal.dto.RevealRequestDto.DetailRevealReqDto;
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

        return reveal;
    }

    @Transactional
    public List<Reveal> findAllByPetAndDateLessThanSortedByDate(Pet pet, Integer date) {
        return revealRepository.findAllByPetAndDateLessThanOrderByDateDesc(pet, date);
    }

    @Transactional
    public void patReveal(Long id) {
        Reveal reveal = revealRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));

        reveal.setPat(true);

        revealRepository.save(reveal);
    }

    @Transactional
    public void feedReveal(Long id) {
        Reveal reveal = revealRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));

        reveal.setFeed(true);

        revealRepository.save(reveal);
    }

    @Transactional
    public void walkReveal(Long id) {
        Reveal reveal = revealRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));

        reveal.setWalk(true);

        revealRepository.save(reveal);
        }

    @Transactional
    public void snackReveal(Long id) {
        Reveal reveal = revealRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));

        reveal.setSnack(true);

        revealRepository.save(reveal);
    }

    @Transactional
    public Reveal writeReveal(Long id, DetailRevealReqDto detailRevealReqDto) {
        Reveal reveal = revealRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));

        reveal.setDiaryContent(detailRevealReqDto.getDiaryContent());
        reveal.setPickEmotion(PickEmotion.valueOf(detailRevealReqDto.getPickEmotion()));
        reveal.setResultEmotion(ResultEmotion.valueOf(detailRevealReqDto.getResultEmotion()));

        revealRepository.save(reveal);

        return reveal;
    }

    @Transactional
    public Reveal findById(Long id) {
        return revealRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));
    }

    @Transactional
    public Double calculateEmotionPercentage(Long id) {
        Reveal reveal = revealRepository.findById(id)
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
