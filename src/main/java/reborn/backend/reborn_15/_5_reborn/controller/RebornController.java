package reborn.backend.reborn_15._5_reborn.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.pet.domain.Pet;
import reborn.backend.pet.service.PetService;
import reborn.backend.reborn_15._5_reborn.converter.RebornConverter;
import reborn.backend.reborn_15._5_reborn.domain.Reborn;
import reborn.backend.reborn_15._5_reborn.dto.RebornRequestDto.ContentRebornReqDto;
import reborn.backend.reborn_15._5_reborn.dto.RebornRequestDto.RebornRebornReqDto;
import reborn.backend.reborn_15._5_reborn.dto.RebornResponseDto.DetailRebornDto;
import reborn.backend.reborn_15._5_reborn.service.RebornService;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;

@Tag(name = "reborn", description = "reborn 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reborn/reborn")
public class RebornController {

    private final RebornService rebornService;
    private final UserService userService;
    private final PetService petService;

    @Operation(summary = "건강한 작별하기 최초 생성 메서드", description = "건강한 작별하기 최초로 만드는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2011", description = "건강한 작별하기 생성이 완료되었습니다.")
    })
    @PostMapping("/create")
    public ApiResponse<Integer> create(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());

        Reborn reborn = rebornService.createReborn(pet);

        petService.updateDate(user.getContentPetId());

        return ApiResponse.onSuccess(SuccessCode.REBORN_CREATED, reborn.getDate());
    }

    @Operation(summary = "특정 건강한 작별하기 조회 메서드", description = "특정 건강한 작별하기를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2002", description = "건강한 작별하기 조회가 완료되었습니다.")
    })
    @GetMapping("view/{id}")
    public ApiResponse<DetailRebornDto> getDetailReBORN(
            @PathVariable(name = "id") Long id
    ){
        Reborn reborn = rebornService.findById(id);
        DetailRebornDto detailRebornDto = RebornConverter.toDetailRebornDto(reborn);

        return ApiResponse.onSuccess(SuccessCode.REBORN_DETAIL_VIEW_SUCCESS, detailRebornDto);
    }

    @Operation(summary = "작별인사 작성 메서드", description = "작별인사 작성하는 메서드입니다.")
    @ApiResponses(value =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2003", description = "작별인사 작성이 완료되었습니다.")
    })
    @PostMapping("/write")
    public ApiResponse<Boolean> write(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody ContentRebornReqDto rebornReqDto
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        rebornService.writeReborn(pet.getRebornDate(), rebornReqDto, pet);

        return ApiResponse.onSuccess(SuccessCode.REBORN_WRITE_COMPLETED, true);
    }

    @Operation(summary = "리본 선택 메서드", description = "리본을 선택하는 메서드입니다.")
    @ApiResponses(value =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2009", description = "리본 선택이 완료되었습니다.")
    })
    @PostMapping("/set")
    public ApiResponse<Boolean> setReborn(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody RebornRebornReqDto rebornReqDto
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        rebornService.setReborn(pet.getRebornDate(), rebornReqDto, pet);

        return ApiResponse.onSuccess(SuccessCode.REBORN_SET_REBORN_COMPLETED, true);
    }

    @Operation(summary = "쓰다듬기 메서드", description = "쓰다듬기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2004", description = "쓰다듬기가 완료되었습니다.")
    })
    @PostMapping("/pat")
    public ApiResponse<Boolean> pat(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        rebornService.patReborn(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REBORN_PAT_COMPLETED, true);
    }

    @Operation(summary = "밥주기 메서드", description = "밥주기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2005", description = "밥주기가 완료되었습니다.")
    })
    @PostMapping("/feed")
    public ApiResponse<Boolean> feed(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        rebornService.feedReborn(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REBORN_FEED_COMPLETED, true);
    }

    @Operation(summary = "씻겨주기 메서드", description = "씻겨주기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2006", description = "씻겨주기가 완료되었습니다.")
    })
    @PostMapping("/wash")
    public ApiResponse<Boolean> wash(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        rebornService.washReborn(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REBORN_WASH_COMPLETED, true);
    }

    @Operation(summary = "옷 입혀주기 메서드", description = "옷 입혀주기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2007", description = "옷 입혀주기가 완료되었습니다.")
    })
    @PostMapping("/clothe")
    public ApiResponse<Boolean> clothe(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        rebornService.clotheReborn(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REBORN_CLOTHE_COMPLETED, true);
    }

    @Operation(summary = "15일 컨텐츠 종료 메서드", description = "15일 컨텐츠 종료하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2008", description = "15일 콘텐츠가 완료되었습니다.")
    })
    @PostMapping("finish")
    public ApiResponse<Boolean> finish(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        petService.updateDate(user.getContentPetId());
        petService.findById(user.getContentPetId()).setProgressState("END");
        userService.resetContentPetId(user);

        return ApiResponse.onSuccess(SuccessCode.REBORN_FINISH_COMPLETED, true);
    }


    @Operation(summary = "인트로 메서드", description = "쓰다듬기로 넘어가는 메서드입니다..")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2010", description = "쓰다듬기로 넘어가기가 완료되었습니다.")
    })
    @PostMapping("/intro")
    public ApiResponse<Boolean> intro(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        rebornService.introReborn(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REBORN_INTRO_COMPLETED, true);
    }

    @Operation(summary = "아웃트로 메서드", description = "아웃트로로 넘어가는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2011", description = "아웃트로로 넘어가기가 완료되었습니다.")
    })
    @PostMapping("/outro")
    public ApiResponse<Boolean> outro(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        rebornService.outroReborn(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REBORN_PAT_COMPLETED, true);
    }

}
