package reborn.backend.fcm.dto;

//import com.google.firebase.messaging.Notification;
import lombok.Getter;
import lombok.Setter;

/*
프론트엔드가 디바이스 토큰과 title과 body를 넘겨 주는 걸로 되어있는데, 실제로는 미리 토큰을 redis나 db나 컬럼에 저장을 해두는 것이 좋다.
title과 body가
*/

@Getter
@Setter
public class FcmRequestDto {
    private String deviceToken;
    private String title;
    private String body;
}

/*
@Builder
public record FCMRequestDto (String deviceToken, String title, String body) {

    //todo client한테 기기 식별 token 전달받아 Member entity에 저장
    public Notification toNotification() {
        return Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build();
    }
}
*/