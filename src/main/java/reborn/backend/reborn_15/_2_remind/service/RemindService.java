package reborn.backend.reborn_15._2_remind.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._2_remind.converter.RemindConverter;
import reborn.backend.reborn_15._2_remind.domain.Remind;
import reborn.backend.reborn_15._2_remind.dto.RemindRequestDto.DetailRemindReqDto;
import reborn.backend.reborn_15._2_remind.dto.RemindRequestDto.RemindReqDto;
import reborn.backend.reborn_15._2_remind.repository.RemindRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemindService {

    private final RemindRepository remindRepository;

    @Transactional
    public Remind createRemind(RemindReqDto remindReqDto, Pet pet) {
        Remind remind = RemindConverter.toRemind(remindReqDto, pet);

        remindRepository.save(remind);

        return remind;
    }

    @Transactional
    public Remind patRemind(Long id) {
        Remind remind = remindRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMIND_NOT_FOUND));

        remind.setPat(true);

        remindRepository.save(remind);

        return remind;
    }

    @Transactional
    public  Remind feedRemind(Long id) {
        Remind remind = remindRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMIND_NOT_FOUND));

        remind.setFeed(true);

        remindRepository.save(remind);

        return remind;
    }

    @Transactional
    public  Remind walkRemind(Long id) {
        Remind reveal = remindRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMIND_NOT_FOUND));

        reveal.setWalk(true);

        remindRepository.save(reveal);

        return reveal;
    }

    @Transactional
    public  Remind snackRemind(Long id) {
        Remind remind = remindRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMIND_NOT_FOUND));

        remind.setSnack(true);

        remindRepository.save(remind);

        return remind;
    }

    @Transactional
    public Remind writeRemind(Long id, DetailRemindReqDto detailRemindReqDto) {
        Remind remind = remindRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMIND_NOT_FOUND));

        remind.setQuestion(detailRemindReqDto.getQuestion());
        remind.setAnswer(detailRemindReqDto.getAnswer());

        remindRepository.save(remind);

        return remind;
    }

    @Transactional
    public Remind findById(Long id) {
        return remindRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMIND_NOT_FOUND));
    }
}
