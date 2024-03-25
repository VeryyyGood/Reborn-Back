package reborn.backend.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "토큰", description = "access token 관련 api 입니다.")
@RestController
public class TokenController {

    @Operation(summary = "토큰 반환", description = "로컬에서 로그인했을때 토큰 반환받는 메서드입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_200", description = "Success"),
    })
    @GetMapping("/token/token")
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
}
