package reborn.backend.reborn_15._4_remember.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._4_remember.converter.RememberConverter;
import reborn.backend.reborn_15._4_remember.domain.Remember;
import reborn.backend.reborn_15._4_remember.dto.RememberRequestDto.DetailRememberReqDto;
import reborn.backend.reborn_15._4_remember.dto.RememberRequestDto.RememberReqDto;
import reborn.backend.reborn_15._4_remember.repository.RememberRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RememberService {

    private final RememberRepository rememberRepository;

    @Transactional
    public Remember createRemember(RememberReqDto remberReqDto, Pet pet) {
        Optional<Remember> latestRemember = rememberRepository.findTopByPetOrderByDateDesc(pet);

        Integer newDate = latestRemember.map(r -> r.getDate() + 1).orElse(12);

        Remember remember = RememberConverter.toRemember(remberReqDto, pet, newDate);

        rememberRepository.save(remember);

        return remember;
    }

    @Transactional
    public Remember patRemember(Long id) {
        Remember remember = rememberRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.setPat(true);

        rememberRepository.save(remember);

        return remember;
    }

    @Transactional
    public Remember feedRemember(Long id) {
        Remember remember = rememberRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.setFeed(true);

        rememberRepository.save(remember);

        return remember;
    }

    @Transactional
    public  Remember walkRemember(Long id) {
        Remember remember = rememberRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.setWalk(true);

        rememberRepository.save(remember);

        return remember;
    }

    @Transactional
    public Remember snackRemember(Long id) {
        Remember remember = rememberRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.setSnack(true);

        rememberRepository.save(remember);

        return remember;
    }

    @Transactional
    public Remember clean_Remember1(Long id) {
        Remember remember = rememberRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.setClean_1(true);

        rememberRepository.save(remember);

        return remember;
    }

    @Transactional
    public Remember clean_Remember2(Long id) {
        Remember remember = rememberRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.setClean_2(true);

        rememberRepository.save(remember);

        return remember;
    }

    @Transactional
    public Remember clean_Remember3(Long id) {
        Remember remember = rememberRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.setClean_3(true);

        rememberRepository.save(remember);

        return remember;
    }

    @Transactional
    public Remember clean_Remember4(Long id) {
        Remember remember = rememberRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.setClean_4(true);

        rememberRepository.save(remember);

        return remember;
    }

    @Transactional
    public Remember clean_Remember5(Long id) {
        Remember remember = rememberRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.setClean_5(true);

        rememberRepository.save(remember);

        return remember;
    }

    @Transactional
    public Remember clean_Remember6(Long id) {
        Remember remember = rememberRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.setClean_6(true);

        rememberRepository.save(remember);

        return remember;
    }

    @Transactional
    public Remember writeRemember(Long id, DetailRememberReqDto detailRememberReqDto) {
        Remember remember = rememberRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.setContent(detailRememberReqDto.getContent());
        remember.setRememberImage(detailRememberReqDto.getRememberImage());

        rememberRepository.save(remember);

        return remember;
    }

    @Transactional
    public Remember findById(Long id){
        return rememberRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));
    }
}
