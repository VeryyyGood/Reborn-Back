package reborn.backend.reveal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.reveal.converter.RevealConverter;
import reborn.backend.reveal.domain.Reveal;
import reborn.backend.reveal.dto.RevealRequestDto.DetailRevealReqDto;
import reborn.backend.reveal.dto.RevealRequestDto.RevealReqDto;
import reborn.backend.reveal.dto.RevealResponseDto.DetailRevealDto;
import reborn.backend.reveal.service.RevealService;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "나의 감정 들여다보기", description = "나의 감정 들여다보기 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reveal")
public class RevealController {

    private final UserService userService;
    private final RevealService revealService;

    // reveal 만들기
    @Operation(summary = "나의 감정 들여다보기 만들기 메서드", description = "나의 감정 들여다보기 만드는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2011", description = "나의 감정 들여다보기 생성이 완료되었습니다.")
    })
    @PostMapping("/create")
    public ApiResponse<Double> create(
            @RequestBody RevealReqDto revealReqDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Reveal reveal = revealService.createReveal(revealReqDto, user);

        double emotionPercentage = revealService.calculateEmotionPercentage(reveal.getRevealId());

        return ApiResponse.onSuccess(SuccessCode.REVEAL_CREATED, emotionPercentage);
    }

    // 모든 reveal 가져오기
    @Operation(summary = "나의 감정 들여다보기 조회 메서드", description = "나의 감정 들여다보기 목록을 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2001", description = " 나의 감정 들여다보기 목록 조회가 완료되었습니다.")
    })
    @GetMapping("/list")
    public ApiResponse<List<DetailRevealDto>> getAllReveal(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        List<Reveal> reveals = revealService.findAllByUserSortedByCreatedAt(user);
        List<DetailRevealDto> revealDtos = reveals.stream()
                .map(RevealConverter::toDetailRevealDto)
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(SuccessCode.REVEAL_LIST_VIEW_SUCCESS, revealDtos);
    }

    // 특정 reveal 가져오기
    @Operation(summary = "특정 나의 감정 들여다보기 조회 메서드", description = "특정 나의 감정 들여다보기를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2002", description = " 나의 감정 들여다보기 조회가 완료되었습니다.")
    })
    @GetMapping("/{id}")
    public ApiResponse<DetailRevealDto> getDetailReveal(
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        Reveal reveal = revealService.findById(id);
        DetailRevealDto detailRevealDto = RevealConverter.toDetailRevealDto(reveal);

        return ApiResponse.onSuccess(SuccessCode.REVEAL_DETAIL_VIEW_SUCCESS, detailRevealDto);
    }


    // reveal 수정하기
    @Operation(summary = "나의 감정 들여다보기 수정 메서드", description = "나의 감정 들여다보기 수정하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REVEAL_2003", description = "나의 감정 들여다보기 수정이 완료되었습니다.")
    })
    @PostMapping("/update/{id}")
    public ApiResponse<Double> update(
            @PathVariable(name = "id") Long id,
            @RequestBody DetailRevealReqDto detailRevealReqDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        Reveal reveal = revealService.updateReveal(id, detailRevealReqDto);

        double emotionPercentage = revealService.calculateEmotionPercentage(reveal.getRevealId());

        return ApiResponse.onSuccess(SuccessCode.REVEAL_UPDATED, emotionPercentage);
    }
}
