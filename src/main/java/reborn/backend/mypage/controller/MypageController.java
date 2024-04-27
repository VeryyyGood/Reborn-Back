package reborn.backend.mypage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.pet.converter.PetConverter;
import reborn.backend.pet.domain.Pet;
import reborn.backend.pet.domain.PetType;
import reborn.backend.pet.dto.PetResponseDto.DetailPetDto;
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
import reborn.backend.reborn_15._5_reborn.service.RebornService;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;

import java.util.List;
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
    public ApiResponse<List<DetailPetDto>> getAllPet(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        List<Pet> pets = petService.findAllByUserSortedByCreatedAt(user);

        List<DetailPetDto> petDtos = pets.stream()
                .map(PetConverter::toDetailPetDto)
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



}
