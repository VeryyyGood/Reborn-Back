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
import reborn.backend.reborn_15._3_reveal.dto.RevealRequestDto.DetailRevealReqDto;
import reborn.backend.reborn_15._3_reveal.dto.RevealRequestDto.RevealReqDto;
import reborn.backend.reborn_15._3_reveal.dto.RevealResponseDto.DetailRevealDto;
import reborn.backend.reborn_15._3_reveal.service.RevealService;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;


@Tag(name = "reveal", description = "reveal 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reborn/{pet-id}/reveal")
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
    public ApiResponse<Boolean> create(
            @PathVariable(name = "pet-id") Long petId,
            @RequestBody RevealReqDto revealReqDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(petId);

        Reveal reveal = revealService.createReveal(revealReqDto, pet);

        return ApiResponse.onSuccess(SuccessCode.REVEAL_CREATED, true);
    }

    @Operation(summary = "특정 나의 감정 들여다보기 조회 메서드", description = "특정 나의 감정 들여다보기를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2002", description = "나의 감정 들여다보기 조회가 완료되었습니다.")
    })
    @GetMapping("view/{id}")
    public ApiResponse<DetailRevealDto> getDetailReveal(
            @PathVariable(name = "pet-id") Long petId,
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
    @PostMapping("/write/{id}")
    public ApiResponse<Double> write(
            @PathVariable(name = "pet-id") Long petId,
            @PathVariable(name = "id") Long id,
            @RequestBody DetailRevealReqDto detailRevealReqDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        Reveal reveal = revealService.writeReveal(id, detailRevealReqDto);

        double emotionPercentage = revealService.calculateEmotionPercentage(reveal.getId());

        return ApiResponse.onSuccess(SuccessCode.REVEAL_WRITE_COMPLETED, emotionPercentage);
    }

    @Operation(summary = "쓰다듬기 메서드", description = "쓰다듬기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2004", description = "쓰다듬기가 완료되었습니다.")
    })
    @PostMapping("/pat/{id}")
    public ApiResponse<Boolean> pat(
            @PathVariable(name = "pet-id") Long petId,
            @PathVariable(name = "id") Long id
    ){
        revealService.patReveal(id);

        return ApiResponse.onSuccess(SuccessCode.REVEAL_PAT_COMPLETED, true);
    }

    @Operation(summary = "밥주기 메서드", description = "밥주기 메서드입니다.")
    @ApiResponses(value =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2005", description = "밥주기가 완료되었습니다.")
    })
    @PostMapping("/feed/{id}")
    public ApiResponse<Boolean> feed(
            @PathVariable(name = "pet-id") Long petId,
            @PathVariable(name = "id") Long id
    ){
        revealService.feedReveal(id);

        return ApiResponse.onSuccess(SuccessCode.REVEAL_FEED_COMPLETED, true);
    }

    @Operation(summary = "산책하기 메서드", description = "산책하기 메서드입니다.")
    @ApiResponses(value =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2006", description = "산책하기가 완료되었습니다.")
    })
    @PostMapping("/walk/{id}")
    public ApiResponse<Boolean> walk(
            @PathVariable(name = "pet-id") Long petId,
            @PathVariable(name = "id") Long id
    ){
        revealService.walkReveal(id);

        return ApiResponse.onSuccess(SuccessCode.REVEAL_WALK_COMPLETED, true);
    }

    @Operation(summary = "간식주기 메서드", description = "간식주기 메서드입니다.")
    @ApiResponses(value =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2007", description = "간식주기가 완료되었습니다.")
    })
    @PostMapping("/snack/{id}")
    public ApiResponse<Boolean> snack(
            @PathVariable(name = "pet-id") Long petId,
            @PathVariable(name = "id") Long id
    ){
        revealService.snackReveal(id);

        return ApiResponse.onSuccess(SuccessCode.REVEAL_SNACK_COMPLETED, true);
    }

}
