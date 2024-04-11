package reborn.backend.reborn_15._2_remind.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import reborn.backend.global.api_payload.ApiResponse;
import org.springframework.web.bind.annotation.*;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.pet.domain.Pet;
import reborn.backend.pet.service.PetService;
import reborn.backend.reborn_15._2_remind.converter.RemindConverter;
import reborn.backend.reborn_15._2_remind.domain.Remind;
import reborn.backend.reborn_15._2_remind.dto.RemindRequestDto.DetailRemindReqDto;
import reborn.backend.reborn_15._2_remind.dto.RemindRequestDto.RemindReqDto;
import reborn.backend.reborn_15._2_remind.dto.RemindResponseDto;
import reborn.backend.reborn_15._2_remind.dto.RemindResponseDto.DetailRemindDto;
import reborn.backend.reborn_15._2_remind.service.RemindService;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;

@Tag(name = "remind", description = "remind 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reborn/{pet-id}/remind")
public class RemindController {

    private final RemindService remindService;
    private final UserService userService;
    private final PetService petService;

    @Operation(summary = "충분한 대화 나누기 최초 생성 메서드", description = "충분한 대화 나누기 최초로 만드는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMIND_2011", description = "충분한 대화 나누기 생성이 완료되었습니다.")
    })
    @PostMapping("/create")
    public ApiResponse<Boolean> create(
            @PathVariable(name = "pet-id") Long petId,
            @RequestBody RemindReqDto remindReqDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(petId);

        Remind remind = remindService.createRemind(remindReqDto, pet);

        return ApiResponse.onSuccess(SuccessCode.REMIND_CREATED, true);
    }

    @Operation(summary = "특정 충분한 대화 나누기 조회 메서드", description = "특정 충분한 대화 나누기를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMIND_2002", description = "충분한 대화 나누기 조회가 완료되었습니다.")
    })
    @GetMapping("view/{id}")
    public ApiResponse<DetailRemindDto> getDetailRemind(
            @PathVariable(name = "pet-id") Long petId,
            @PathVariable(name = "id") Long id
    ){
        Remind remind = remindService.findById(id);
        DetailRemindDto detailRemindDto = RemindConverter.toDetailRemindDto(remind);

        return ApiResponse.onSuccess(SuccessCode.REMIND_DETAIL_VIEW_SUCCESS, detailRemindDto);
    }


    @Operation(summary = "쓰다듬기 메서드", description = "쓰다듬기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMIND_2004", description = "쓰다듬기가 완료되었습니다.")
    })
    @PostMapping("/pat/{id}")
    public ApiResponse<Boolean> pat(
            @PathVariable(name = "pet-id") Long petId,
            @PathVariable(name = "id") Long id
    ){
        remindService.patRemind(id);

        return ApiResponse.onSuccess(SuccessCode.REMIND_PAT_COMPLETED, true);
    }

    @Operation(summary = "밥주기 메서드", description = "밥주기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMIND_2005", description = "밥주기가 완료되었습니다.")
    })
    @PostMapping("/feed/{id}")
    public ApiResponse<Boolean> feed(
            @PathVariable(name = "pet-id") Long petId,
            @PathVariable(name = "id") Long id
    ){
        remindService.feedRemind(id);

        return ApiResponse.onSuccess(SuccessCode.REMIND_FEED_COMPLETED, true);
    }

    @Operation(summary = "산책하기 메서드", description = "산책하기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMIND_2006", description = "산책하기가 완료되었습니다.")
    })
    @PostMapping("/walk/{id}")
    public ApiResponse<Boolean> walk(
            @PathVariable(name = "pet-id") Long petId,
            @PathVariable(name = "id") Long id
    ){
        remindService.walkRemind(id);

        return ApiResponse.onSuccess(SuccessCode.REMIND_WALK_COMPLETED, true);
    }

    @Operation(summary = "간식주기 메서드", description = "간식주기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMIND_2007", description = "간식주기가 완료되었습니다.")
    })
    @PostMapping("/snack/{id}")
    public ApiResponse<Boolean> snack(
            @PathVariable(name = "pet-id") Long petId,
            @PathVariable(name = "id") Long id
    ){
        remindService.snackRemind(id);

        return ApiResponse.onSuccess(SuccessCode.REMIND_SNACK_COMPLETED, true);
    }

    @Operation(summary = "답변 작성 메서드", description = "답변을 작성하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMIND_2008", description = "답변 작성이 완료되었습니다.")
    })
    @PostMapping("/write/{id}")
    public ApiResponse<Boolean> write(
            @PathVariable(name = "pet-id") Long petId,
            @PathVariable(name = "id") Long id,
            @RequestBody DetailRemindReqDto detailRemindReqDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        Remind remind = remindService.writeRemind(id, detailRemindReqDto);

        return ApiResponse.onSuccess(SuccessCode.REMIND_WRITE_COMPLETED, true);
    }
}
