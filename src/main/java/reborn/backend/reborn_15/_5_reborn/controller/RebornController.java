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
import reborn.backend.reborn_15._5_reborn.dto.RebornRequestDto.DetailRebornReqDto;
import reborn.backend.reborn_15._5_reborn.dto.RebornRequestDto.RebornReqDto;
import reborn.backend.reborn_15._5_reborn.dto.RebornResponseDto;
import reborn.backend.reborn_15._5_reborn.dto.RebornResponseDto.DetailRebornDto;
import reborn.backend.reborn_15._5_reborn.service.RebornService;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;

@Tag(name = "reborn", description = "reborn 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reborn/{pet-id}/reborn")
public class RebornController {

    private final RebornService rebornService;
    private final UserService userService;
    private final PetService petService;

    @Operation(summary = "건강한 작별하기 최초 생성 메서드", description = "건강한 작별하기 최초로 만드는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2011", description = "건강한 작별하기 생성이 완료되었습니다.")
    })
    @PostMapping("/create")
    public ApiResponse<Boolean> create(
            @PathVariable(name = "pet-id") Long petId,
            @RequestBody RebornReqDto rebornReqDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(petId);

        Reborn reborn= rebornService.createReborn(rebornReqDto, pet);

        petService.updateDate(petId);

        return ApiResponse.onSuccess(SuccessCode.REBORN_CREATED, true);
    }

    @Operation(summary = "특정 건강한 작별하기 조회 메서드", description = "특정 건강한 작별하기를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2002", description = "건강한 작별하기 조회가 완료되었습니다.")
    })
    @GetMapping("view/{id}")
    public ApiResponse<DetailRebornDto> getDetailReBORN(
            @PathVariable(name = "pet-id") Long petId,
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
    @PostMapping("/write/{id}")
    public ApiResponse<Boolean> write(
            @PathVariable(name = "pet-id") Long petId,
            @PathVariable(name = "id") Long id,
            @RequestBody DetailRebornReqDto detailRebornReqDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        Reborn reborn = rebornService.writeReborn(id, detailRebornReqDto);

        return ApiResponse.onSuccess(SuccessCode.REBORN_WRITE_COMPLETED, true);
    }

    @Operation(summary = "쓰다듬기 메서드", description = "쓰다듬기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2004", description = "쓰다듬기가 완료되었습니다.")
    })
    @PostMapping("/pat/{id}")
    public ApiResponse<Boolean> pat(
            @PathVariable(name = "pet-id") Long petId,
            @PathVariable(name = "id") Long id
    ){
        rebornService.patReborn(id);
        return ApiResponse.onSuccess(SuccessCode.REBORN_PAT_COMPLETED, true);
    }

    @Operation(summary = "밥주기 메서드", description = "밥주기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2005", description = "밥주기가 완료되었습니다.")
    })
    @PostMapping("/feed/{id}")
    public ApiResponse<Boolean> feed(
            @PathVariable(name = "pet-id") Long petId,
            @PathVariable(name = "id") Long id
    ){
        rebornService.feedReborn(id);

        return ApiResponse.onSuccess(SuccessCode.REBORN_FEED_COMPLETED, true);
    }

    @Operation(summary = "씻겨주기 메서드", description = "씻겨주기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2006", description = "씻겨주기가 완료되었습니다.")
    })
    @PostMapping("/wash/{id}")
    public ApiResponse<Boolean> wash(
            @PathVariable(name = "pet-id") Long petId,
            @PathVariable(name = "id") Long id
    ){
        rebornService.washReborn(id);
        return ApiResponse.onSuccess(SuccessCode.REBORN_WASH_COMPLETED, true);
    }

    @Operation(summary = "털 빗겨주기 메서드", description = "털 빗겨주기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REBORN_2007", description = "털 빗겨주기가 완료되었습니다.")
    })
    @PostMapping("/brush/{id}")
    public ApiResponse<Boolean> brush(
            @PathVariable(name = "pet-id") Long petId,
            @PathVariable(name = "id") Long id
    ){
        rebornService.brushReborn(id);
        return ApiResponse.onSuccess(SuccessCode.REBORN_BRUSH_COMPLETED, true);
    }
}
