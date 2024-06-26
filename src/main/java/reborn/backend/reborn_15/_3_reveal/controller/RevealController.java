package reborn.backend.reborn_15._3_reveal.controller;

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
import reborn.backend.reborn_15._3_reveal.converter.RevealConverter;
import reborn.backend.reborn_15._3_reveal.domain.Reveal;
import reborn.backend.reborn_15._3_reveal.dto.RevealRequestDto.RevealReqDto;
import reborn.backend.reborn_15._3_reveal.dto.RevealResponseDto.DetailRevealDto;
import reborn.backend.reborn_15._3_reveal.service.RevealService;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;


@Tag(name = "reveal", description = "reveal 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reborn/reveal")
public class RevealController {

    private final RevealService revealService;
    private final UserService userService;
    private final PetService petService;

    // reveal 만들기
    @Operation(summary = "나의 감정 들여다보기 최초 생성 메서드", description = "나의 감정 들여다보기 최초로 만드는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2011", description = "나의 감정 들여다보기 생성이 완료되었습니다.")
    })
    @PostMapping("/create")
    public ApiResponse<Integer> create(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());

        Reveal reveal = revealService.createReveal(pet);

        petService.updateDate(user.getContentPetId());

        return ApiResponse.onSuccess(SuccessCode.REVEAL_CREATED, reveal.getDate());
    }

    @Operation(summary = "특정 나의 감정 들여다보기 조회 메서드", description = "특정 나의 감정 들여다보기를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2002", description = "나의 감정 들여다보기 조회가 완료되었습니다.")
    })
    @GetMapping("view/{id}")
    public ApiResponse<DetailRevealDto> getDetailReveal(
            @PathVariable(name = "id") Long id
    ){
        Reveal reveal = revealService.findById(id);
        DetailRevealDto detailRevealDto = RevealConverter.toDetailRevealDto(reveal);

        return ApiResponse.onSuccess(SuccessCode.REVEAL_DETAIL_VIEW_SUCCESS, detailRevealDto);
    }

    @Operation(summary = "일기 작성 메서드", description = "일기를 작성하는 메서드입니다.")
    @ApiResponses(value =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2003", description = "일기 작성이 완료되었습니다.")
    })
    @PostMapping("/write")
    public ApiResponse<Double> write(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody RevealReqDto revealReqDto
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        revealService.writeReveal(pet.getRebornDate(), revealReqDto, pet);

        double emotionPercentage = revealService.calculateEmotionPercentage(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REVEAL_WRITE_COMPLETED, emotionPercentage);
    }

    @Operation(summary = "쓰다듬기 메서드", description = "쓰다듬기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2004", description = "쓰다듬기가 완료되었습니다.")
    })
    @PostMapping("/pat")
    public ApiResponse<Boolean> pat(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        revealService.patReveal(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REVEAL_PAT_COMPLETED, true);
    }

    @Operation(summary = "밥주기 메서드", description = "밥주기 메서드입니다.")
    @ApiResponses(value =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2005", description = "밥주기가 완료되었습니다.")
    })
    @PostMapping("/feed")
    public ApiResponse<Boolean> feed(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        revealService.feedReveal(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REVEAL_FEED_COMPLETED, true);
    }

    @Operation(summary = "산책하기 메서드", description = "산책하기 메서드입니다.")
    @ApiResponses(value =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2006", description = "산책하기가 완료되었습니다.")
    })
    @PostMapping("/walk")
    public ApiResponse<Boolean> walk(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        revealService.walkReveal(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REVEAL_WALK_COMPLETED, true);
    }

    @Operation(summary = "간식주기 메서드", description = "간식주기 메서드입니다.")
    @ApiResponses(value =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2007", description = "간식주기가 완료되었습니다.")
    })
    @PostMapping("/snack")
    public ApiResponse<Boolean> snack(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        revealService.snackReveal(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REVEAL_SNACK_COMPLETED, true);
    }

    @Operation(summary = "인트로 메서드", description = "쓰다듬기로 넘어가는 메서드입니다..")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2008", description = "쓰다듬기로 넘어가기가 완료되었습니다.")
    })
    @PostMapping("/intro")
    public ApiResponse<Boolean> intro(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        revealService.introReveal(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REVEAL_INTRO_COMPLETED, true);
    }

    @Operation(summary = "놀아주기 메서드", description = "놀아주기 메서드입니다.")
    @ApiResponses(value =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2009", description = "놀아주기가 완료되었습니다.")
    })
    @PostMapping("/play")
    public ApiResponse<Boolean> play(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());
        revealService.walkReveal(pet.getRebornDate(), pet);

        return ApiResponse.onSuccess(SuccessCode.REVEAL_PLAY_COMPLETED, true);
    }
}
