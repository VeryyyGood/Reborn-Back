package reborn.backend.rediary.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.rediary.converter.RediaryConverter;
import reborn.backend.rediary.domain.Rediary;
import reborn.backend.rediary.dto.RediaryRequestDto.DetailRediaryReqDto;
import reborn.backend.rediary.dto.RediaryRequestDto.RediaryReqDto;
import reborn.backend.rediary.repository.RediaryRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RediaryService {
    private final RediaryRepository rediaryRepository;

    @Transactional
    public Rediary createRediary(RediaryReqDto rediaryReqDto) {
        Rediary rediary = RediaryConverter.toRediary(rediaryReqDto);
        rediaryRepository.save(rediary);

        return rediary;
    }

    @Transactional
    public Rediary updateRediary(Long id, DetailRediaryReqDto detailRediaryReqDto) {
        Rediary rediary = rediaryRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REDIARY_NOT_FOUND));

        rediary.setRediaryTitle(detailRediaryReqDto.getRediaryTitle());
        rediary.setRediaryContents(detailRediaryReqDto.getRediaryContents());
        rediary.setRediaryCreatedAt(detailRediaryReqDto.getRediaryCreatedAt());
        rediary.setEmotionStatus(detailRediaryReqDto.getEmotionStatus());

        rediaryRepository.save(rediary);

        return rediary;
    }

    @Transactional
    public List<Rediary> findAllSortedByCreatedAt() {
        return rediaryRepository.findAllByOrderByCreatedAtDesc();
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

}
