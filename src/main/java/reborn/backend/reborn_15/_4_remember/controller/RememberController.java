package reborn.backend.reborn_15._4_remember.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.pet.domain.Pet;
import reborn.backend.pet.service.PetService;
import reborn.backend.reborn_15._4_remember.converter.RememberConverter;
import reborn.backend.reborn_15._4_remember.domain.Remember;
import reborn.backend.reborn_15._4_remember.dto.RememberRequestDto.SimpleRememberReqDto;
import reborn.backend.reborn_15._4_remember.dto.RememberResponseDto.DetailRememberDto;
import reborn.backend.reborn_15._4_remember.service.RememberService;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;

import java.io.IOException;

@Tag(name = "remember", description = "remember 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reborn/remember")
public class RememberController {

    private final RememberService rememberService;
    private final UserService userService;
    private final PetService petService;

    @Operation(summary = "건강한 작별 준비하기 최초 생성 메서드", description = "건강한 작별 준비하기 최초로 만드는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMEMBER_2011", description = "건강한 작별 준비하기 생성이 완료되었습니다.")
    })
    @PostMapping("/create")
    public ApiResponse<Boolean> create(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());

        rememberService.createRemember(pet);

        petService.updateDate(user.getContentPetId());

        return ApiResponse.onSuccess(SuccessCode.REMEMBER_CREATED, true);
    }

    @Operation(summary = "특정 건강한 작별 준비하기 조회 메서드", description = "특정 건강한 작별 준비하기를 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMEMBER_2002", description = "건강한 작별 준비하기 조회가 완료되었습니다.")
    })
    @GetMapping("view/{id}")
    public ApiResponse<DetailRememberDto> getDetailRemember(
            @PathVariable(name = "id") Long id
    ){
        Remember remember = rememberService.findById(id);
        DetailRememberDto detailRememberDto = RememberConverter.toDetailRememberDto(remember);

        return ApiResponse.onSuccess(SuccessCode.REMEMBER_DETAIL_VIEW_SUCCESS, detailRememberDto);
    }

    @Operation(summary = "그림일기 작성 메서드", description = "그림일기 작성하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMEMBER_2003", description = "그림일기 작성이 완료되었습니다.")
    })
    @PostMapping(value = "/write/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Boolean> write(
            @RequestPart(value = "remember") MultipartFile file,
            @RequestPart("data") SimpleRememberReqDto simpleRememberReqDto,
            @PathVariable(name = "id") Long id
    ) throws IOException {
        String dirName = "remember/";
        rememberService.writeRemember(id, simpleRememberReqDto, dirName, file);

        return ApiResponse.onSuccess(SuccessCode.REMEMBER_WRITE_COMPLETED, true);
    }

    @Operation(summary = "쓰다듬기 메서드", description = "쓰다듬기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMEMBER_2004", description = "쓰다듬기가 완료되었습니다.")
    })
    @PostMapping("/pat/{id}")
    public ApiResponse<Boolean> pat(
            @PathVariable(name = "id") Long id
    ){
        rememberService.patRemember(id);

        return ApiResponse.onSuccess(SuccessCode.REMEMBER_PAT_COMPLETED, true);
    }

    @Operation(summary = "밥주기 메서드", description = "밥주기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMEMBER_2005", description = "밥주기가 완료되었습니다.")
    })
    @PostMapping("/feed/{id}")
    public ApiResponse<Boolean> feed(
            @PathVariable(name = "id") Long id
    ){
        rememberService.feedRemember(id);

        return ApiResponse.onSuccess(SuccessCode.REMEMBER_FEED_COMPLETED, true);
    }

    @Operation(summary = "산책하기 메서드", description = "산책하기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMEMBER_2006", description = "산책하기가 완료되었습니다.")
    })
    @PostMapping("/walk/{id}")
    public ApiResponse<Boolean> walk(
            @PathVariable(name = "id") Long id
    ){
        rememberService.walkRemember(id);

        return ApiResponse.onSuccess(SuccessCode.REMEMBER_WALK_COMPLETED, true);
    }

    @Operation(summary = "간식주기 메서드", description = "간식주기 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMEMBER_2007", description = "간식주기가 완료되었습니다.")
    })
    @PostMapping("/snack/{id}")
    public ApiResponse<Boolean> snack(
            @PathVariable(name = "id") Long id
    ){
        rememberService.snackRemember(id);

        return ApiResponse.onSuccess(SuccessCode.REMEMBER_SNACK_COMPLETED, true);
    }

    @Operation(summary = "정리1 메서드", description = "정리1 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMEMBER_2008", description = "정리1가 완료되었습니다.")
    })
    @PostMapping("/clean1/{id}")
    public ApiResponse<Boolean> clean1(
            @PathVariable(name = "id") Long id
    ){
        rememberService.clean_Remember1(id);

        return ApiResponse.onSuccess(SuccessCode.REMEMBER_CLEAN1_COMPLETED, true);
    }

    @Operation(summary = "정리2 메서드", description = "정리2 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMEMBER_2009", description = "정리2가 완료되었습니다.")
    })
    @PostMapping("/clean2/{id}")
    public ApiResponse<Boolean> clean2(
            @PathVariable(name = "id") Long id
    ){
        rememberService.clean_Remember2(id);

        return ApiResponse.onSuccess(SuccessCode.REMEMBER_CLEAN2_COMPLETED, true);
    }

    @Operation(summary = "정리3 메서드", description = "정리3 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMEMBER_2010", description = "정리3가 완료되었습니다.")
    })
    @PostMapping("/clean3/{id}")
    public ApiResponse<Boolean> clean3(
            @PathVariable(name = "id") Long id
    ){
        rememberService.clean_Remember3(id);

        return ApiResponse.onSuccess(SuccessCode.REMEMBER_CLEAN3_COMPLETED, true);
    }

    @Operation(summary = "정리4 메서드", description = "정리4 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMEMBER_2011", description = "정리4가 완료되었습니다.")
    })
    @PostMapping("/clean4/{id}")
    public ApiResponse<Boolean> clean4(
            @PathVariable(name = "id") Long id
    ){
        rememberService.clean_Remember4(id);

        return ApiResponse.onSuccess(SuccessCode.REMEMBER_CLEAN4_COMPLETED, true);
    }

    @Operation(summary = "정리5 메서드", description = "정리5 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMEMBER_2012", description = "정리5가 완료되었습니다.")
    })
    @PostMapping("/clean5/{id}")
    public ApiResponse<Boolean> clean5(
            @PathVariable(name = "id") Long id
    ){
        rememberService.clean_Remember5(id);

        return ApiResponse.onSuccess(SuccessCode.REMEMBER_CLEAN5_COMPLETED, true);
    }

    @Operation(summary = "정리6 메서드", description = "정리6 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REMEMBER_2013", description = "정리6가 완료되었습니다.")
    })
    @PostMapping("/clean6/{id}")
    public ApiResponse<Boolean> clean6(
            @PathVariable(name = "id") Long id
    ){
        rememberService.clean_Remember6(id);

        return ApiResponse.onSuccess(SuccessCode.REMEMBER_CLEAN6_COMPLETED, true);
    }

}
