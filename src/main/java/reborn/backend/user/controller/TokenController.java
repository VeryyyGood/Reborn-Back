package reborn.backend.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.user.converter.UserConverter;
import reborn.backend.user.domain.User;
import reborn.backend.user.dto.UserRequestDto.UserReqDto;
import reborn.backend.user.dto.JwtDto;
import reborn.backend.user.service.UserService;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "토큰", description = "access token 관련 api 입니다.")
@RestController
@RequestMapping("/token")
public class TokenController {

    private final UserService userService;
    @Operation(summary = "토큰 반환", description = "프론트에게 유저 정보 받아 토큰 반환하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_2011", description = "회원가입 & 로그인 성공"),
    })
    @PostMapping("/generate")
    public ApiResponse<JwtDto> tokenToFront(
            @RequestBody UserReqDto userReqDto // email, username, nickname, provider
    ) {
        // 1. 받은 email 가지고 회원가입 되어있는 사용자인지 판단
        Boolean isMember = userService.checkMemberByEmail(userReqDto.getEmail());

        // 2. jwt 생성
        String accessToken = "";
        String refreshToken = "";

        if(isMember){ // jwt 재생성 -> 성공
            // user 찾기
            User user = userService.findByEmail(userReqDto.getEmail());
            // jwt 생성
            JwtDto jwt = userService.jwtMakeSave(userReqDto.getUsername());
            // 변수에 저장
            accessToken = jwt.getAccessToken();
            refreshToken = jwt.getRefreshToken();

        } else{ // 회원 가입 이후 jwt 생성 (db 접근 해야함)
            // db에 정보 넣기(= 회원가입)
            User user = userService.createUser(userReqDto);
            // jwt 생성
            JwtDto jwt = userService.jwtMakeSave(userReqDto.getUsername());
            // 변수에 저장
            accessToken = jwt.getAccessToken();
            refreshToken = jwt.getRefreshToken();
        }

        // 3. 생성한 토큰을 리다이렉트 필요 시 추가

        return ApiResponse.onSuccess(SuccessCode.USER_LOGIN_SUCCESS, UserConverter.jwtDto(accessToken, refreshToken));
    }

    @Operation(summary = "토큰 반환", description = "로컬에서 로그인했을때 토큰 반환하는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "토큰 반환 Success"),
    })
    @GetMapping("/local")
    public ResponseEntity<Map<String, String>> tokenPage(
            @RequestParam(name = "access-token") String accessToken,
            @RequestParam(name = "refresh-token") String refreshToken
    ) {
        // 결과 데이터를 Map에 담아 반환
        Map<String, String> responseData = new HashMap<>();
        responseData.put("accessToken", accessToken);
        responseData.put("refreshToken", refreshToken);

        // JSON 형태로 응답
        return ResponseEntity.ok(responseData);
    }

    public TokenController(UserService userService) {
        this.userService = userService;
    }
}
