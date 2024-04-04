package reborn.backend.pet.controller;

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
import reborn.backend.pet.dto.PetRequestDto.DetailPetReqDto;
import reborn.backend.pet.dto.PetRequestDto.PetReqDto;
import reborn.backend.pet.dto.PetResponseDto.DetailPetDto;
import reborn.backend.pet.service.PetService;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "반려동물 정보", description = "반려동물 정보 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {

    private final UserService userService;
    private final PetService petService;

    // Pet 새로 만들기
    @Operation(summary = "반려동물 정보 입력 메서드", description = "반려동물 정보를 입력하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PET_2011", description = "반려동물 정보 입력이 완료되었습니다.")
    })
    @PostMapping("/create")
    public ApiResponse<Boolean> create(
            @RequestBody PetReqDto petReqDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        petService.createPet(petReqDto, user);

        return ApiResponse.onSuccess(SuccessCode.PET_CREATED, true);
    }

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
    @GetMapping("/{id}")
    public ApiResponse<DetailPetDto> getDetailPet(
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        Pet pet = petService.findById(id);
        DetailPetDto detailPetDto = PetConverter.toDetailPetDto(pet);

        return ApiResponse.onSuccess(SuccessCode.PET_DETAIL_VIEW_SUCCESS, detailPetDto);


    }

    // Pet 수정하기
    @Operation(summary = "반려동물 정보 수정 메서드", description = "반려동물 정보를 수정하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PET_2003", description = "반려동물 정보 수정이 완료되었습니다.")
    })
    @PostMapping("/update/{id}")
    public ApiResponse<Boolean> update(
            @PathVariable(name = "id") Long id,
            @RequestBody DetailPetReqDto detailPetReqDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        petService.updatePet(id, detailPetReqDto);

        return ApiResponse.onSuccess(SuccessCode.PET_UPDATED, true);
    }


}
