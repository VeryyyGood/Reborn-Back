package reborn.backend.reborn_15._4_remember.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.exception.GeneralException;
import reborn.backend.global.s3.AmazonS3Manager;
import reborn.backend.pet.domain.Pet;
import reborn.backend.reborn_15._4_remember.converter.RememberConverter;
import reborn.backend.reborn_15._4_remember.domain.Remember;
import reborn.backend.reborn_15._4_remember.dto.RememberRequestDto.SimpleRememberReqDto;
import reborn.backend.reborn_15._4_remember.repository.RememberRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RememberService {

    private final RememberRepository rememberRepository;
    private final AmazonS3Manager amazonS3Manager;

    @Transactional
    public Remember createRemember(Pet pet) {
        Optional<Remember> latestRemember = rememberRepository.findTopByPetOrderByDateDesc(pet);

        Integer newDate = latestRemember.map(r -> r.getDate() + 1).orElse(12);

        Remember remember = RememberConverter.toRemember(pet, newDate);

        rememberRepository.save(remember);

        pet.setProgressState("INTRO");

        return remember;
    }


    @Transactional
    public void introRemember(Integer date, Pet pet) {
        Remember remember = rememberRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.getPet().setProgressState("PAT");
    }


    @Transactional
    public List<Remember> findAllByPetAndDateLessThanSortedByDate(Pet pet, Integer date) {
        return rememberRepository.findAllByPetAndDateLessThanOrderByDateDesc(pet, date);
    }

    @Transactional
    public void patRemember(Integer date, Pet pet) {
        Remember remember = rememberRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.setPat(true);

        remember.getPet().setProgressState("FEED");

        rememberRepository.save(remember);
    }

    @Transactional
    public void feedRemember(Integer date, Pet pet) {
        Remember remember = rememberRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.setFeed(true);

        remember.getPet().setProgressState("WALK");

        rememberRepository.save(remember);
    }

    @Transactional
    public void walkRemember(Integer date, Pet pet) {
        Remember remember = rememberRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.setWalk(true);

        remember.getPet().setProgressState("SNACK");

        rememberRepository.save(remember);
    }

    @Transactional
    public void snackRemember(Integer date, Pet pet) {
        Remember remember = rememberRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.setSnack(true);

        remember.getPet().setProgressState("IMAGE");

        rememberRepository.save(remember);
    }

    @Transactional
    public void clean_Remember(Integer date, Pet pet) {
        Remember remember = rememberRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        remember.setClean(true);

        remember.getPet().setProgressState("FINISH");

        rememberRepository.save(remember);
    }

    @Transactional
    public void writeRemember(Integer date, SimpleRememberReqDto simpleRememberReqDto, String dirName, MultipartFile file, Pet pet) throws IOException {
        Remember remember = rememberRepository.findByPetAndDate(pet, date)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));

        String uploadFileUrl = null;

        if (file != null && !file.isEmpty()) {
            String contentType = file.getContentType();
            if (ObjectUtils.isEmpty(contentType)) { // 확장자명이 존재하지 않을 경우 취소 처리
                throw GeneralException.of(ErrorCode.INVALID_FILE_CONTENT_TYPE_REMEMBER);
            }
            java.io.File uploadFile = amazonS3Manager.convert(file)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

            String fileName = dirName + amazonS3Manager.generateFileName(file);
            uploadFileUrl = amazonS3Manager.putS3(uploadFile, fileName);
        }

        remember.setTitle(simpleRememberReqDto.getTitle());
        remember.setContent(simpleRememberReqDto.getContent());
        remember.setImageDate(simpleRememberReqDto.getImageDate());
        remember.setRememberImage(uploadFileUrl);

        rememberRepository.save(remember);

        remember.getPet().setProgressState("CLEAN");
    }

    @Transactional
    public Remember findById(Long id){
        return rememberRepository.findById(id)
                .orElseThrow(() -> GeneralException.of(ErrorCode.REMEMBER_NOT_FOUND));
    }
}
