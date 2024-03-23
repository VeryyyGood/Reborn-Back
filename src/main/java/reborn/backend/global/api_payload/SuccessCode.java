package reborn.backend.global.api_payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode implements BaseCode {
    // Common
    OK(HttpStatus.OK, "COMMON_200", "Success"),
    CREATED(HttpStatus.CREATED, "COMMON_201", "Created"),

    // User
    USER_CREATED(HttpStatus.CREATED, "USER_201", "회원가입이 완료되었습니다."),
    USER_LOGOUT_SUCCESS(HttpStatus.OK, "USER_200", "로그아웃 되었습니다."),
    USER_DELETE_SUCCESS(HttpStatus.OK, "USER_200", "회원탈퇴가 완료되었습니다."),

    // Rediary
    REDIARY_CREATED(HttpStatus.CREATED, "REDIARY_2011", "감정 일기 생성이 완료되었습니다."),
    REDIARY_LIST_VIEW_SUCCESS(HttpStatus.OK, "REDIARY_2001", "감정 일기 목록 조회가 완료되었습니다."),
    REDIARY_DETAIL_VIEW_SUCCESS(HttpStatus.OK, "REDIARY_2002", "감정 일기 조회가 완료되었습니다."),
    REDIARY_UPDATED(HttpStatus.OK, "REDIARY_2003", "감정 일기 수정이 완료되었습니다."),
    REDIARY_DELETED(HttpStatus.OK, "REDIARY_2004", "감정 일기 삭제가 완료되었습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .httpStatus(this.httpStatus)
                .isSuccess(true)
                .code(this.code)
                .message(this.message)
                .build();
    }
}