package reborn.backend.rediary.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.rediary.converter.RediaryConverter;
import reborn.backend.global.entity.PickEmotion;
import reborn.backend.rediary.domain.Rediary;
import reborn.backend.global.entity.ResultEmotion;
import reborn.backend.rediary.dto.RediaryRequestDto.DetailRediaryReqDto;
import reborn.backend.rediary.dto.RediaryRequestDto.RediaryReqDto;
import reborn.backend.rediary.repository.RediaryRepository;
import reborn.backend.user.domain.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RediaryService {

    private final RediaryRepository rediaryRepository;

    @Transactional
    public Rediary createRediary(RediaryReqDto rediaryReqDto, User user) {
        Rediary rediary = RediaryConverter.toRediary(rediaryReqDto, user);

        // 생성일 필드를 현재 날짜로 설정
        rediary.setRediaryCreatedAt(LocalDate.now());

        rediaryRepository.save(rediary);

        return rediary;
    }

    @Transactional
    public Rediary updateRediary(Long id, DetailRediaryReqDto detailRediaryReqDto) {
        Rediary rediary = rediaryRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REDIARY_NOT_FOUND));

        if( Objects.equals(rediary.getRediaryCreatedAt(), LocalDate.now()) )
        {
            rediary.setRediaryTitle(detailRediaryReqDto.getRediaryTitle());
            rediary.setRediaryContent(detailRediaryReqDto.getRediaryContent());
            rediary.setPickEmotion(PickEmotion.valueOf(detailRediaryReqDto.getPickEmotion()));
            rediary.setResultEmotion(ResultEmotion.valueOf(detailRediaryReqDto.getResultEmotion()));

            rediaryRepository.save(rediary);

            return rediary;
        }
        return rediary;
    }

    @Transactional
    public List<Rediary> findAllByUserSortedByCreatedAt(User user) {
        return rediaryRepository.findAllByRediaryWriterOrderByCreatedAtDesc(user.getUsername());
    }

    @Transactional
    public Rediary findById(Long id){
        return rediaryRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REDIARY_NOT_FOUND));
    }

    @Transactional
    public void deleteRediary(Long id) {
        Rediary rediary = rediaryRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REDIARY_NOT_FOUND));
        rediaryRepository.delete(rediary);
    }

    @Transactional
    public List<Rediary> findAllByCreatedAt(Long id) {
        Rediary rediary = rediaryRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REDIARY_NOT_FOUND));

        return rediaryRepository.findAllByRediaryCreatedAt(rediary.getRediaryCreatedAt());
    }

    @Transactional
    public Double calculateEmotionPercentage(Long id) {
        Rediary rediary = rediaryRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REDIARY_NOT_FOUND));

        List<Rediary> allRediariesByCreatedAt = findAllByCreatedAt(id);

        long countSelectedEmotion = allRediariesByCreatedAt.stream()
                .filter(r -> r.getResultEmotion() == rediary.getResultEmotion())
                .count();

        long totalTodayRediaries = allRediariesByCreatedAt.size();

        return (double) countSelectedEmotion / totalTodayRediaries * 100.0;
    }

    @Transactional
    public Boolean findByToday(User user) {
        List<Rediary> todayRediaries = rediaryRepository.findAllByRediaryWriterOrderByCreatedAtDesc(user.getUsername());
        LocalDate today = LocalDate.now();

        long count = todayRediaries.stream()
                .filter(rediary -> rediary.getRediaryCreatedAt().isEqual(today))
                .count();

        return count == 0;
    }
}
