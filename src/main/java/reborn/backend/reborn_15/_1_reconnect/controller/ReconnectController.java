package reborn.backend.reborn_15._1_reconnect.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.pet.domain.Pet;
import reborn.backend.pet.dto.PetRequestDto.DetailPetReqDto;
import reborn.backend.pet.dto.PetRequestDto.PetReqDto;
import reborn.backend.pet.service.PetService;
import reborn.backend.user.domain.User;
import reborn.backend.user.jwt.CustomUserDetails;
import reborn.backend.user.service.UserService;

@Tag(name = "reconnect", description = "reconnect 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/reborn/reconnect")
public class ReconnectController {

    private final PetService petService;
    private final UserService userService;

    // Pet 새로 만들기
    @Operation(summary = "나의 반려동물과 만나기 메서드", description = "나의 반려동물과 만나기를 생성하는 메서드입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "RECONNECT_2011", description = "나의 반려동물과 만나기 생성이 완료되었습니다.")
    })
    @PostMapping("/create")
    public ApiResponse<Boolean> create(
            @RequestBody PetReqDto petReqDto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        User user = userService.findUserByUserName(customUserDetails.getUsername());

        Pet pet = petService.createPet(petReqDto, user);
        userService.setContentPetId(user, pet.getId());

        return ApiResponse.onSuccess(SuccessCode.RECONNECT_CREATED, true);
    }

}
