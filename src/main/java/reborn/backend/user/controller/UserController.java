package reborn.backend.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.SuccessCode;
import reborn.backend.user.jwt.JwtDto;
import reborn.backend.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @DeleteMapping("/logout")
    public ApiResponse<Integer> logout(HttpServletRequest request) {
        userService.logout(request);
        return ApiResponse.onSuccess(SuccessCode.USER_LOGOUT_SUCCESS, 1);
    }

    @PostMapping("/reissue")
    public ApiResponse<JwtDto> reissue(
            HttpServletRequest request
    ) {
        JwtDto jwt = userService.reissue(request);
        return ApiResponse.onSuccess(SuccessCode.USER_REISSUE_SUCCESS, jwt);
    }

    @DeleteMapping("/me")
    public ApiResponse<Integer> deleteUser(Authentication auth) {
        userService.deleteUser(auth.getName());
        return ApiResponse.onSuccess(SuccessCode.USER_DELETE_SUCCESS, 1);
    }
}