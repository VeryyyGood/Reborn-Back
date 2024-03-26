package reborn.backend.rediary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.rediary.converter.RediaryConverter;
import reborn.backend.rediary.domain.Rediary;
import reborn.backend.rediary.dto.RediaryRequestDto.DetailRediaryReqDto;
import reborn.backend.rediary.dto.RediaryRequestDto.RediaryReqDto;
import reborn.backend.rediary.dto.RediaryResponseDto.DetailRediaryDto;
import reborn.backend.rediary.dto.RediaryResponseDto.SimpleRediaryDto;
import reborn.backend.rediary.service.RediaryService;

import java.util.List;
import java.util.stream.Collectors;

/*
@Tag(name = "감정일기", description = "감정 일기 관련 api 입니다.")
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/rediary")
public class RediaryController {

    private final RediaryService rediaryService;


    /*
    @Operation(summary = "감정 일기 만들기 메서드", description = "감정 일기를 만드는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REDIARY_2011", description = "감정 일기 생성이 완료되었습니다.")
    })
    */
    @PostMapping("/create")
    public ApiResponse<SimpleRediaryDto> create(
            @RequestBody RediaryReqDto rediaryReqDto
    ){
        Rediary rediary = rediaryService.createRediary(rediaryReqDto);

        return ApiResponse.onSuccess(SuccessCode.REDIARY_CREATED, RediaryConverter.toSimpleRediaryDto(rediary));
    }


    /*
    @Operation(summary = "감정 일기 조회 메서드", description = "감정 일기 목록을 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REDIARY_2001", description = "감정 일기 목록 조회가 완료되었습니다.")
    })
    */
    @GetMapping("/list")
    public ApiResponse<List<DetailRediaryDto>> getAllRediary() {
        List<Rediary> rediaries = rediaryService.findAllSortedByCreatedAt();
        List<DetailRediaryDto> rediaryDtos = rediaries.stream()
                .map(RediaryConverter::toDetailRediaryDto)
                .collect(Collectors.toList());

        return ApiResponse.onSuccess(SuccessCode.REDIARY_LIST_VIEW_SUCCESS, rediaryDtos);
    }


    /*
    @Operation(summary = "특정 감정 일기 조회 메서드", description = "특정 감정 일기를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REDIARY_2002", description = "감정 읽기 조회가 완료되었습니다.")
    })
     */
    @GetMapping("/{id}")
    public ApiResponse<DetailRediaryDto> getDetailRediary(
            @PathVariable(name = "id") Long id
    ){
        Rediary rediary = rediaryService.findById(id);
        DetailRediaryDto detailRediaryDto = RediaryConverter.toDetailRediaryDto(rediary);

        return ApiResponse.onSuccess(SuccessCode.REDIARY_DETAIL_VIEW_SUCCESS, detailRediaryDto);
    }


    /*
    @Operation(summary = "감정 일기 수정 메서드", description = "감정 일기를 수정하는 메서드입니다.")
    @ApiResponses(value =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REDIARY_2003", description = "감정 일기 수정이 완료되었습니다.")
    })
     */
    @PostMapping("/update/{id}")
    public ApiResponse<DetailRediaryDto> update(
            @PathVariable(name = "id") Long id,
            @RequestBody DetailRediaryReqDto detailRediaryReqDto
    ){
        Rediary rediary = rediaryService.updateRediary(id, detailRediaryReqDto);

        return ApiResponse.onSuccess(SuccessCode.REDIARY_UPDATED, RediaryConverter.toDetailRediaryDto(rediary));
    }


    /*
    @Operation(summary = "감정 일기 삭제 메서드", description = "감정 일기를 삭제하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REDIARY_2004", description = "감정 일기 삭제가 완료되었습니다.")
    })
     */
    @DeleteMapping("/delete/{id}")
    public ApiResponse<Integer> delete(
            @PathVariable(name = "id") Long id
    ){
        rediaryService.deleteRediary(id);

        return ApiResponse.onSuccess(SuccessCode.REDIARY_DELETED, 1);
    }
}