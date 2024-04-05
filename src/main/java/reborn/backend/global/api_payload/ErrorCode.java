package reborn.backend.global.api_payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseCode {
    // Common
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 에러, 서버 개발자에게 문의하세요."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_4041", "존재하지 않는 회원입니다."),

    // Pet
    PET_NOT_FOUND(HttpStatus.NOT_FOUND, "PET_4041", "존재하지 않는 반려동물입니다."),

    // Jwt
    WRONG_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "JWT_4041", "일치하는 리프레시 토큰이 없습니다."),
    IP_NOT_MATCHED(HttpStatus.FORBIDDEN, "JWT_4042", "리프레시 토큰의 IP주소가 일치하지 않습니다."),
    TOKEN_INVALID(HttpStatus.FORBIDDEN, "JWT_4043", "유효하지 않은 토큰입니다."),
    TOKEN_NO_AUTH(HttpStatus.FORBIDDEN, "JWT_4031", "권한 정보가 없는 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT_4011", "토큰 유효기간이 만료되었습니다."),

    // Board
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "BOARD_4041", "존재하지 않는 게시물입니다."),
    BOARD_WRONG_TYPE(HttpStatus.BAD_REQUEST, "BOARD_4001", "존재하지 않는 게시물 종류 입니다."),
    BOARD_WRONG_SORTING_WAY(HttpStatus.BAD_REQUEST, "BOARD_4002", "존재하지 않는 게시물 정렬 방법 입니다."),

    // Comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_4041", "존재하지 않는 댓글입니다."),

    // Rediary
    REDIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "REDIARY_4041", "존재하지 않는 감정 일기입니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .httpStatus(this.httpStatus)
                .isSuccess(false)
                .code(this.code)
                .message(this.message)
                .build();
    }
}
