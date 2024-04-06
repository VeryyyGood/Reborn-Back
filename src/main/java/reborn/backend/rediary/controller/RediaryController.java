package reborn.backend.rediary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.rediary.converter.RediaryConverter;
import reborn.backend.rediary.domain.Rediary;
import reborn.backend.rediary.dto.RediaryRequestDto.DetailRediaryReqDto;
import reborn.backend.rediary.dto.RediaryRequestDto.RediaryReqDto;
import reborn.backend.rediary.dto.RediaryResponseDto.DetailRediaryDto;
import reborn.backend.rediary.service.RediaryService;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "감정일기", description = "감정 일기 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rediary")
public class RediaryController {

    private final UserService userService;
    private final RediaryService rediaryService;

    // 금일 감정 일기 작성 여부 -> false(일기 작성됨), true(일기 작성되지 않음)
    @Operation(summary = "금일 감정 일기 작성 여부 확인 여부 메서드", description = "금일 감정 일기 작성 여부를 확인하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REDIARY_2006", description = "금일 감정 일기 작성 여부가 확인되었습니다.")
    })
    @GetMapping("/check")
    public ApiResponse<Boolean> check(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Boolean isWrittenToday = rediaryService.findByToday(user);

        return ApiResponse.onSuccess(SuccessCode.REDIARY_TODAY_WRITTEN_CHECKD, isWrittenToday);
    }


    // rediary 새로 만들기
    @Operation(summary = "감정 일기 만들기 메서드", description = "감정 일기를 만드는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REDIARY_2011", description = "감정 일기 생성이 완료되었습니다.")
    })
    @PostMapping("/create")
    public ApiResponse<Double> create(
            @RequestBody RediaryReqDto rediaryReqDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Rediary rediary = rediaryService.createRediary(rediaryReqDto, user);

        double emotionPercentage = rediaryService.calculateEmotionPercentage(rediary.getRediaryId());

        return ApiResponse.onSuccess(SuccessCode.REDIARY_CREATED, emotionPercentage);
    }

    // 모든 rediary 가져오기
    @Operation(summary = "감정 일기 조회 메서드", description = "감정 일기 목록을 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REDIARY_2001", description = "감정 일기 목록 조회가 완료되었습니다.")
    })
    @GetMapping("/list")
    public ApiResponse<List<DetailRediaryDto>> getAllRediary(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
            ) {
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        List<Rediary> rediaries = rediaryService.findAllByUserSortedByCreatedAt(user);
        List<DetailRediaryDto> rediaryDtos = rediaries.stream()
                .map(RediaryConverter::toDetailRediaryDto)
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(SuccessCode.REDIARY_LIST_VIEW_SUCCESS, rediaryDtos);
    }

    // 특정 rediary 가져오기
    @Operation(summary = "특정 감정 일기 조회 메서드", description = "특정 감정 일기를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REDIARY_2002", description = "감정 일기 조회가 완료되었습니다.")
    })
    @GetMapping("/{id}")
    public ApiResponse<DetailRediaryDto> getDetailRediary(
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        Rediary rediary = rediaryService.findById(id);
        DetailRediaryDto detailRediaryDto = RediaryConverter.toDetailRediaryDto(rediary);

        return ApiResponse.onSuccess(SuccessCode.REDIARY_DETAIL_VIEW_SUCCESS, detailRediaryDto);
    }

    // rediary 수정하기
    @Operation(summary = "감정 일기 수정 메서드", description = "감정 일기를 수정하는 메서드입니다.")
    @ApiResponses(value =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REDIARY_2003", description = "감정 일기 수정이 완료되었습니다.")
    })
    @PostMapping("/update/{id}")
    public ApiResponse<Double> update(
            @PathVariable(name = "id") Long id,
            @RequestBody DetailRediaryReqDto detailRediaryReqDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        Rediary rediary = rediaryService.updateRediary(id, detailRediaryReqDto);

        double emotionPercentage = rediaryService.calculateEmotionPercentage(rediary.getRediaryId());


        return ApiResponse.onSuccess(SuccessCode.REDIARY_UPDATED, emotionPercentage);
    }

    // rediary 삭제하기
    @Operation(summary = "감정 일기 삭제 메서드", description = "감정 일기를 삭제하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REDIARY_2004", description = "감정 일기 삭제가 완료되었습니다.")
    })
    @DeleteMapping("/delete/{id}")
    public ApiResponse<Boolean> delete(
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        rediaryService.deleteRediary(id);

        return ApiResponse.onSuccess(SuccessCode.REDIARY_DELETED, true);
    }
}