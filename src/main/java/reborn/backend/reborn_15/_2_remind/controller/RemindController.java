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
import reborn.backend.reborn_15._2_remind.dto.RemindRequestDto.RemindReqDto;
import reborn.backend.reborn_15._2_remind.dto.RemindResponseDto.DetailRemindDto;
import reborn.backend.reborn_15._2_remind.service.RemindService;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;

@Tag(name = "remind", description = "remind 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reborn/remind")
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
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());

        remindService.createRemind(pet);

        petService.updateDate(user.getContentPetId());

        return ApiResponse.onSuccess(SuccessCode.REMIND_CREATED, true);
    }

    @Operation(summary = "특정 충분한 대화 나누기 조회 메서드", description = "특정 충분한 대화 나누기를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMIND_2002", description = "충분한 대화 나누기 조회가 완료되었습니다.")
    })
    @GetMapping("view/{id}")
    public ApiResponse<DetailRemindDto> getDetailRemind(
            @PathVariable(name = "id") Long id
    ){
        Remind remind = remindService.findById(id);
        DetailRemindDto detailRemindDto = RemindConverter.toDetailRemindDto(remind);

        return ApiResponse.onSuccess(SuccessCode.REMIND_DETAIL_VIEW_SUCCESS, detailRemindDto);
    }

    @Operation(summary = "답변 작성 메서드", description = "답변을 작성하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMIND_2003", description = "답변 작성이 완료되었습니다.")
    })
    @PostMapping("/write")
    public ApiResponse<Boolean> write(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody RemindReqDto remindReqDto
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        remindService.writeRemind(pet.getRebornDate(), remindReqDto, pet);

        return ApiResponse.onSuccess(SuccessCode.REMIND_WRITE_COMPLETED, true);
    }

    @Operation(summary = "쓰다듬기 메서드", description = "쓰다듬기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMIND_2004", description = "쓰다듬기가 완료되었습니다.")
    })
    @PostMapping("/pat")
    public ApiResponse<Boolean> pat(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        remindService.patRemind(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REMIND_PAT_COMPLETED, true);
    }

    @Operation(summary = "밥주기 메서드", description = "밥주기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMIND_2005", description = "밥주기가 완료되었습니다.")
    })
    @PostMapping("/feed")
    public ApiResponse<Boolean> feed(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        remindService.feedRemind(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REMIND_FEED_COMPLETED, true);
    }

    @Operation(summary = "산책하기 메서드", description = "산책하기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMIND_2006", description = "산책하기가 완료되었습니다.")
    })
    @PostMapping("/walk")
    public ApiResponse<Boolean> walk(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        remindService.walkRemind(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REMIND_WALK_COMPLETED, true);
    }

    @Operation(summary = "간식주기 메서드", description = "간식주기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMIND_2007", description = "간식주기가 완료되었습니다.")
    })
    @PostMapping("/snack")
    public ApiResponse<Boolean> snack(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        remindService.snackRemind(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REMIND_SNACK_COMPLETED, true);
    }

    @Operation(summary = "인트로 메서드", description = "쓰다듬기로 넘어가는 메서드입니다..")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMIND_2008", description = "쓰다듬기로 넘어가기가 완료되었습니다.")
    })
    @PostMapping("/intro")
    public ApiResponse<Boolean> intro(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        remindService.introRemind(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REMIND_INTRO_COMPLETED, true);
    }
}
