package reborn.backend.pet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.pet.converter.PetConverter;
import reborn.backend.pet.domain.Pet;
import reborn.backend.pet.dto.PetResponseDto.PetNameDto;
import reborn.backend.pet.service.PetService;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;


@Tag(name = "반려동물 정보", description = "반려동물 정보 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {

    private final UserService userService;
    private final PetService petService;

    @Operation(summary = "반려동물 이름 조회", description = "반려동물 이름을 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "REDIARY_2004", description = "반려동물 이름 조회가 완료되었습니다.")
    })
    @GetMapping("/name")
    public ApiResponse<PetNameDto> name(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
            ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());
        Pet pet = petService.findById(user.getContentPetId());

        PetNameDto petNameDto = PetConverter.toPetNameDto(pet);

        return ApiResponse.onSuccess(SuccessCode.PET_NAME_VIEW_SUCCESS, petNameDto);
    }

}
