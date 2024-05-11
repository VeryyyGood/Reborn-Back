package reborn.backend.fcm.dto;
/*
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

*/
/*
fcm에 메시지를 보내느 뼈대가 되는 구조
image: 선택사항
validateOnly: 메시지를 실제로 보내는 대신, 유효성 검사만 실행하려면 true로 설정 -> 보통 false로 고정함
*//*


@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
    private boolean validateOnly;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private Notification notification;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }
}*/
