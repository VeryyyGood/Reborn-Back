package reborn.backend.mypage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.pet.converter.PetConverter;
import reborn.backend.pet.domain.Pet;
import reborn.backend.global.entity.PetType;
import reborn.backend.pet.dto.PetResponseDto.DetailPetDto;
import reborn.backend.pet.dto.PetResponseDto.SimplePetDto;
import reborn.backend.pet.service.PetService;
import reborn.backend.reborn_15._2_remind.converter.RemindConverter;
import reborn.backend.reborn_15._2_remind.domain.Remind;
import reborn.backend.reborn_15._2_remind.dto.RemindResponseDto.SimpleRemindDto;
import reborn.backend.reborn_15._2_remind.service.RemindService;
import reborn.backend.reborn_15._3_reveal.converter.RevealConverter;
import reborn.backend.reborn_15._3_reveal.domain.Reveal;
import reborn.backend.reborn_15._3_reveal.dto.RevealResponseDto.SimpleRevealDto;
import reborn.backend.reborn_15._3_reveal.service.RevealService;
import reborn.backend.reborn_15._4_remember.converter.RememberConverter;
import reborn.backend.reborn_15._4_remember.domain.Remember;
import reborn.backend.reborn_15._4_remember.dto.RememberResponseDto.SimpleRememberDto;
import reborn.backend.reborn_15._4_remember.service.RememberService;
import reborn.backend.reborn_15._5_reborn.converter.RebornConverter;
import reborn.backend.reborn_15._5_reborn.domain.Reborn;
import reborn.backend.reborn_15._5_reborn.dto.RebornResponseDto.SimpleRebornDto;
import reborn.backend.reborn_15._5_reborn.service.RebornService;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Tag(name = "마이페이지", description = "마이페이지 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    private final UserService userService;
    private final PetService petService;
    private final RemindService remindService;
    private final RevealService revealService;
    private final RememberService rememberService;
    private final RebornService rebornService;

    // 모든 Pet 가져오기
    @Operation(summary = "반려동물 정보 조회 메서드", description = "반려동물 정보 목록을 조회하는 메서드입니다.")
    @ApiResponses(value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PET_2001", description = "반려동물 정보 목록 조회가 완료되었습니다.")
    })
    @GetMapping("/list")
    public ApiResponse<List<SimplePetDto>> getAllPet(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        List<Pet> pets = petService.findAllByUserSortedByCreatedAt(user);

        List<SimplePetDto> petDtos = pets.stream()
                .map(PetConverter::toSimplePetDto)
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(SuccessCode.PET_LIST_VIEW_SUCCESS, petDtos);
    }

    // 특정 Pet 가져오기
    @Operation(summary = "특정 반려동물 정보 조회 메서드", description = "특정 반려동물 정보를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PET_2002", description = "반려동물 정보 조회가 완료되었습니다.")
    })
    @GetMapping("/list/{pet-id}")
    public ApiResponse<DetailPetDto> getDetailPet(
            @PathVariable(name = "pet-id") Long id
    ){
        Pet pet = petService.findById(id);
        DetailPetDto detailPetDto = PetConverter.toDetailPetDto(pet);

        return ApiResponse.onSuccess(SuccessCode.PET_DETAIL_VIEW_SUCCESS, detailPetDto);
    }

    // 펫 정보 삭제하기
    @Operation(summary = "특정 반려동물 정보 삭제 메서드", description = "특정 반려동물 정보를 삭제하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PET_2003", description = "반려동물 삭제가 완료되었습니다.")
    })
    @DeleteMapping("/delete/{pet-id}")
    public ApiResponse<Boolean> deletePet(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable(name = "pet-id") Long id
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        if (Objects.equals(user.getContentPetId(),id) ) userService.resetContentPetId(user);

        petService.deletePet(id);
        return ApiResponse.onSuccess(SuccessCode.PET_DELETED, true);
    }


    // RECONNECT에 대한 REVIEW
    @Operation(summary = "특정 반려동물의 종류 조회 메서드", description = "특정 반려동물의 종류를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVIEW_2001", description = "나의 반려동물과 만나기 조회가 완료되었습니다.")
    })
    @GetMapping("/reconnect/{pet-id}")
    public ApiResponse<PetType> getReconnect(
            @PathVariable(name = "pet-id") Long id
    ){
        Pet pet = petService.findById(id);

        return ApiResponse.onSuccess(SuccessCode.REVIEW_RECONNECT_VIEW_SUCCESS, pet.getPetType());
    }

    // REMIND에 대한 REVIEW
    @Operation(summary = "충분한 대화 나누기 내용 조회 메서드", description = "충분한 대화 나누기 내용 조회 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVIEW_2002", description = "충분한 대화 나누기 내용 조회가 완료되었습니다.")
    })
    @GetMapping("/remind/{pet-id}")
    public ApiResponse<List<SimpleRemindDto>> getRemind(
            @PathVariable(name = "pet-id") Long id
    ){
        Pet pet = petService.findById(id);

        List<Remind> reminds = remindService.findAllByPetAndDateLessThanSortedByDate(pet, pet.getRebornDate());

        List<SimpleRemindDto> remindDtos = reminds.stream()
                .map(RemindConverter::toSimpleRemindDto)
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(SuccessCode.REVIEW_REMIND_VIEW_SUCCESS, remindDtos);
    }

    // REVEAL에 대한 REVIEW
    @Operation(summary = "나의 감정 들여다보기 조회 메서드", description = "나의 감정 들여다보기 조회 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVIEW_2003", description = "나의 감정 들여다보기 내용 조회가 완료되었습니다.")
    })
    @GetMapping("/reveal/{pet-id}")
    public ApiResponse<List<SimpleRevealDto>> getReveal(
            @PathVariable(name = "pet-id") Long id
    ){
        Pet pet = petService.findById(id);

        List<Reveal> reveals = revealService.findAllByPetAndDateLessThanSortedByDate(pet, pet.getRebornDate());

        List<SimpleRevealDto> revealDtos = reveals.stream()
                .map(RevealConverter::toSimpleRevealDto)
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(SuccessCode.REVIEW_REVEAL_VIEW_SUCCESS, revealDtos);

    }

    // REMEMBER에 대한 REVIEW
    @Operation(summary = "건강한 작별 준비하기 조회 메서드", description = "건강한 작별 준비하기 조회 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVIEW_2004", description = "건강한 작별 준비하기 내용 조회가 완료되었습니다.")
    })
    @GetMapping("/remember/{pet-id}")
    public ApiResponse<List<SimpleRememberDto>> getRemember(
            @PathVariable(name = "pet-id") Long id
    ){
        Pet pet = petService.findById(id);

        List<Remember> remembers = rememberService.findAllByPetAndDateLessThanSortedByDate(pet, pet.getRebornDate());

        List<SimpleRememberDto> rememberDtos = remembers.stream()
                .map(RememberConverter::toSimpleRememberDto)
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(SuccessCode.REVIEW_REMEMBER_VIEW_SUCCESS, rememberDtos);

    }

    // REBORN에 대한 REVIEW
    @Operation(summary = "건강한 작별하기 조회 메서드", description = "건강한 작별하기 조회 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVIEW_2005", description = "건강한 작별하기 내용 조회가 완료되었습니다.")
    })
    @GetMapping("/reborn/{pet-id}")
    public ApiResponse<SimpleRebornDto> getReborn(
            @PathVariable(name = "pet-id") Long id
    ){
        Pet pet = petService.findById(id);

        Reborn reborn = rebornService.findByPetAndDateLessThanSorted(pet, pet.getRebornDate());
        SimpleRebornDto rebornDto = RebornConverter.toSimpleRebornDto(reborn);

        return ApiResponse.onSuccess(SuccessCode.REVIEW_REBORN_VIEW_SUCCESS, rebornDto);
    }

    // REMIND에 대한 CHECK
    @Operation(summary = "충분한 대화 나누기 존재 확인", description = "충분한 대화 나누기 존재 확인 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVIEW_2006", description = "충분한 대화 나누기 존재 확인이 완료되었습니다.")
    })
    @GetMapping("/remind/check/{pet-id}")
    public ApiResponse<Boolean> checkRemind(
            @PathVariable(name = "pet-id") Long id
    ){
        Pet pet = petService.findById(id);

        List<Remind> reminds = remindService.findAllByPetAndDateLessThanSortedByDate(pet, pet.getRebornDate());

        if( reminds.isEmpty() ) return ApiResponse.onSuccess(SuccessCode.REVIEW_REMIND_CHECK_SUCCESS, false);
        else return ApiResponse.onSuccess(SuccessCode.REVIEW_REMIND_CHECK_SUCCESS, true);
    }

    // REVEAL에 대한 CHECK
    @Operation(summary = "나의 감정 들여다보기 존재 확인", description = "나의 감정 들여다보기 존재 확인 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVIEW_2007", description = "나의 감정 들여다보기 존재 확인이 완료되었습니다.")
    })
    @GetMapping("/reveal/check/{pet-id}")
    public ApiResponse<Boolean> checkReveal(
            @PathVariable(name = "pet-id") Long id
    ){
        Pet pet = petService.findById(id);

        List<Reveal> reveals = revealService.findAllByPetAndDateLessThanSortedByDate(pet, pet.getRebornDate());

        if( reveals.isEmpty() ) return ApiResponse.onSuccess(SuccessCode.REVIEW_REVEAL_CHECK_SUCCESS, false);
        else return ApiResponse.onSuccess(SuccessCode.REVIEW_REVEAL_CHECK_SUCCESS, true);
    }

    // REMEMBER에 대한 CHECK
    @Operation(summary = "건강한 작별 준비하기 존재 확인", description = "건강한 작별 준비하기 존재 확인 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVIEW_2008", description = "건강한 작별 준비하기 존재 확인이 완료되었습니다.")
    })
    @GetMapping("/remember/check/{pet-id}")
    public ApiResponse<Boolean> checkRemember(
            @PathVariable(name = "pet-id") Long id
    ){
        Pet pet = petService.findById(id);

        List<Remember> remembers = rememberService.findAllByPetAndDateLessThanSortedByDate(pet, pet.getRebornDate());

        if( remembers.isEmpty() ) return ApiResponse.onSuccess(SuccessCode.REVIEW_REMEMBER_CHECK_SUCCESS, false);
        else return ApiResponse.onSuccess(SuccessCode.REVIEW_REMEMBER_CHECK_SUCCESS, true);
    }

    // REBORN에 대한 CHECK
    @Operation(summary = "건강한 작별하기 존재 확인", description = "건강한 작별하기 존재 확인 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVIEW_2009", description = "건강한 작별하기 존재 확인이 완료되었습니다.")
    })
    @GetMapping("/reborn/check/{pet-id}")
    public ApiResponse<Boolean> checkReborn(
            @PathVariable(name = "pet-id") Long id
    ){
        Pet pet = petService.findById(id);

        Reborn reborn = rebornService.findByPetAndDateLessThanSorted(pet, pet.getRebornDate());

        if( reborn == null ) return ApiResponse.onSuccess(SuccessCode.REVIEW_REBORN_CHECK_SUCCESS, false);
        else return ApiResponse.onSuccess(SuccessCode.REVIEW_REBORN_CHECK_SUCCESS, true);
    }

}
