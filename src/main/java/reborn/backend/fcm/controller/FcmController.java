package reborn.backend.fcm.controller;
/*
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reborn.backend.fcm.service.FcmService;
import reborn.backend.fcm.dto.FcmRequestDto;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fcm")
public class FcmController {

    private final FcmService fcmService;

    @PostMapping("/pushMessage")
    public ResponseEntity pushMessage(@RequestBody FcmRequestDto requestDTO) throws IOException {
        System.out.println(requestDTO.getDeviceToken() + " "
                +requestDTO.getTitle() + " " + requestDTO.getBody());

        fcmService.sendMessageTo(
                requestDTO.getDeviceToken(),
                requestDTO.getTitle(),
                requestDTO.getBody());

        return ResponseEntity.ok().build();
    }
}
*/
