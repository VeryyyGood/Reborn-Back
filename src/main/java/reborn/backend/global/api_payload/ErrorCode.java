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
    USER_NOT_FOUND_BY_EMAIL(HttpStatus.NOT_FOUND, "USER_4042", "존재하지 않는 회원입니다.-EMAIL"),
    USER_NOT_FOUND_BY_USERNAME(HttpStatus.NOT_FOUND, "USER_4043", "존재하지 않는 회원입니다.-USERNAME"),
    ALREADY_USED_NICKNAME(HttpStatus.FORBIDDEN, "USER_4031", "이미 사용중인 닉네임입니다."),

    // File
    MISMATCH_IMAGE_FILE(HttpStatus.FORBIDDEN, "FILE_4031", "첨부할 사진은 png, jpeg 유형만 가능합니다."),

    // Pet
    PET_NOT_FOUND(HttpStatus.NOT_FOUND, "PET_4041", "존재하지 않는 반려동물입니다."),

    // Jwt
    WRONG_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "JWT_4041", "일치하는 리프레시 토큰이 없습니다."),
    IP_NOT_MATCHED(HttpStatus.FORBIDDEN, "JWT_4031", "리프레시 토큰의 IP주소가 일치하지 않습니다."),
    TOKEN_INVALID(HttpStatus.FORBIDDEN, "JWT_4032", "유효하지 않은 토큰입니다."),
    TOKEN_NO_AUTH(HttpStatus.FORBIDDEN, "JWT_4033", "권한 정보가 없는 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT_4011", "토큰 유효기간이 만료되었습니다."),

    // Board
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "BOARD_4041", "존재하지 않는 게시물입니다."),
    BOARD_WRONG_TYPE(HttpStatus.BAD_REQUEST, "BOARD_4001", "존재하지 않는 게시물 종류 입니다."),
    BOARD_WRONG_SORTING_WAY(HttpStatus.BAD_REQUEST, "BOARD_4002", "존재하지 않는 게시물 정렬 방법 입니다."),
    INVALID_FILE_CONTENT_TYPE(HttpStatus.BAD_REQUEST, "BOARD_4003", "잘못된 파일 유형입니다."),

    FILE_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "BOARD_4001", "파일을 첨부하는 과정에서 문제가 발생했습니다."),

    // Comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_4041", "존재하지 않는 댓글입니다."),

    // Rediary
    REDIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "REDIARY_4041", "존재하지 않는 감정 일기입니다."),

    // Reveal
    REVEAL_NOT_FOUND(HttpStatus.NOT_FOUND, "REVEAL_4041", "존재하지 않는 나의 감정 들여다보기입니다."),

    // Remind
    REMIND_NOT_FOUND(HttpStatus.NOT_FOUND, "REMIND_4041", "존재하지 않는 충분한 대화 나누기입니다."),

    // Remember
    REMEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "REMEMBER_4041", "존재하지 않는 건강한 작별 준비하기입니다."),

    // Reborn
    REBORN_NOT_FOUND(HttpStatus.NOT_FOUND,  "REBORN_4041", "존재하지 않는 건강한 작별하기입니다.")
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
