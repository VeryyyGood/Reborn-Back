package reborn.backend.reveal.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.entity.PickEmotion;
import reborn.backend.global.entity.ResultEmotion;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.reveal.converter.RevealConverter;
import reborn.backend.reveal.domain.Reveal;
import reborn.backend.reveal.dto.RevealRequestDto.DetailRevealReqDto;
import reborn.backend.reveal.dto.RevealRequestDto.RevealReqDto;
import reborn.backend.reveal.repository.RevealRepository;
import reborn.backend.user.domain.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RevealService {

    private final RevealRepository revealRepository;

    @Transactional
    public Reveal createReveal(RevealReqDto revealReqDto, User user) {
        Reveal reveal = RevealConverter.toReveal(revealReqDto, user);

        revealRepository.save(reveal);

        return reveal;
    }

    @Transactional
    public Reveal updateReveal(Long id, DetailRevealReqDto detailRevealReqDto) {
        Reveal reveal = revealRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));

        reveal.setRevealContents(detailRevealReqDto.getRevealContents());
        reveal.setRevealCreatedAt(detailRevealReqDto.getRevealCreatedAt());
        reveal.setPickEmotion(PickEmotion.valueOf(detailRevealReqDto.getPickEmotion()));
        reveal.setResultEmotion(ResultEmotion.valueOf(detailRevealReqDto.getResultEmotion()));

        revealRepository.save(reveal);

        return reveal;
    }

    @Transactional
    public List<Reveal> findAllByUserSortedByCreatedAt(User user) {
        return revealRepository.findAllByRevealWriterOrderByCreatedAt(user.getUsername());
    }

    @Transactional
    public Reveal findById(Long id){
        return revealRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));
    }

    @Transactional
    public List<Reveal> findAllByCreatedAt(Long id) {
        Reveal remind = revealRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));
        return revealRepository.findAllByRevealCreatedAt(remind.getRevealCreatedAt());
    }

    @Transactional
    public Double calculateEmotionPercentage(Long id) {
        Reveal reveal = revealRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REVEAL_NOT_FOUND));

        List<Reveal> allRevealsByCreatedAt = findAllByCreatedAt(id);

        long countSelectedEmotion = allRevealsByCreatedAt.stream()
                .filter(r -> r.getResultEmotion() == reveal.getResultEmotion())
                .count();

        long totalTodayReveals = allRevealsByCreatedAt.size();

        return (double) countSelectedEmotion / totalTodayReveals * 100.0;
    }
}
