package reborn.backend.fcm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reborn.backend.fcm.service.FcmService;
import reborn.backend.fcm.dto.FcmRequestDto;
import reborn.backend.global.api_payload.ApiResponse;
import reborn.backend.global.api_payload.SuccessCode;

import java.io.IOException;
@RestController
@RequiredArgsConstructor
@RequestMapping("/fcm")
public class FcmController {
    private final FcmService fcmService;

    // 1. client가 server로 알림 생성 요청
    @PostMapping("/pushMessage")
    public ApiResponse<String> pushMessage(@RequestBody FcmRequestDto requestDTO) throws IOException {
        System.out.println(requestDTO.getDeviceToken() + " "
                +requestDTO.getTitle() + " " + requestDTO.getBody());
        fcmService.sendMessageTo(
                requestDTO.getDeviceToken(),
                requestDTO.getTitle(),
                requestDTO.getBody());
        return ApiResponse.onSuccess(SuccessCode.FCM_SEND_SUCCESS, "fcm alarm success");
    }
}

