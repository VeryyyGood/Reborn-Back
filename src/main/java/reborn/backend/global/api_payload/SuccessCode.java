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
    USER_CREATED(HttpStatus.CREATED, "USER_2011", "회원가입이 완료되었습니다."),
    USER_LOGOUT_SUCCESS(HttpStatus.OK, "USER_2001", "로그아웃 되었습니다."),
    USER_REISSUE_SUCCESS(HttpStatus.OK, "USER_2002", "토큰 재발급이 완료되었습니다."),
    USER_DELETE_SUCCESS(HttpStatus.OK, "USER_2003", "회원탈퇴가 완료되었습니다."),

    // Pet
    PET_CREATED(HttpStatus.CREATED, "PET_2011", "반려동물 정보 입력이 완료되었습니다."),
    PET_LIST_VIEW_SUCCESS(HttpStatus.OK, "PET_2001", "반려동물 정보 목록 조회가 완료되었습니다."),
    PET_DETAIL_VIEW_SUCCESS(HttpStatus.OK, "PET_2002", "반려동물 정보 조회가 완료되었습니다."),
    PET_UPDATED(HttpStatus.OK, "PET_2003", "반려동물 정보 수정이 완료되었습니다."),

    // Board
    BOARD_CREATED(HttpStatus.CREATED, "BOARD_2011", "게시판 생성이 완료되었습니다."),
    BOARD_DETAIL_VIEW_SUCCESS(HttpStatus.OK, "BOARD_2001", "게시판 상세 조회가 완료되었습니다."),
    BOARD_UPDATED(HttpStatus.OK, "BOARD_2002", "게시판 수정이 완료되었습니다."),
    BOARD_DELETED(HttpStatus.OK, "BOARD_2003", "게시판 삭제가 완료되었습니다."),
    BOARD_LIST_VIEW_SUCCESS(HttpStatus.OK, "BOARD_2004", "전체 게시판 리스트 조회가 완료되었습니다."),

    // BoardLike
    BOARD_LIKE_SUCCESS(HttpStatus.OK, "LIKE_2001", "게시판 좋아요가 완료되었습니다."),
    BOARD_UNLIKE_SUCCESS(HttpStatus.OK, "LIKE_2002", "게시판 좋아요 취소가 완료되었습니다."),
    BOARD_LIKE_COUNT_SUCCESS(HttpStatus.OK, "LIKE_2003", "게시판 좋아요 개수 조회가 완료되었습니다."),

    // BoardBookmark
    BOARD_BOOKMARK_SUCCESS(HttpStatus.OK, "BOOKMARK_2001", "게시판 북마크가 완료되었습니다."),
    BOARD_UNBOOKMARK_SUCCESS(HttpStatus.OK, "BOOKMARK_2002", "게시판 북마크 취소가 완료되었습니다."),

    // Comment
    COMMENT_CREATED(HttpStatus.CREATED, "COMMENT_2011", "댓글 생성이 완료되었습니다."),
    COMMENT_DELETED(HttpStatus.OK, "COMMENT_2001", "댓글 삭제가 완료되었습니다."),

    // Rediary
    REDIARY_CREATED(HttpStatus.CREATED, "REDIARY_2011", "감정 일기 생성이 완료되었습니다."),
    REDIARY_LIST_VIEW_SUCCESS(HttpStatus.OK, "REDIARY_2001", "감정 일기 목록 조회가 완료되었습니다."),
    REDIARY_DETAIL_VIEW_SUCCESS(HttpStatus.OK, "REDIARY_2002", "감정 일기 조회가 완료되었습니다."),
    REDIARY_UPDATED(HttpStatus.OK, "REDIARY_2003", "감정 일기 수정이 완료되었습니다."),
    REDIARY_DELETED(HttpStatus.OK, "REDIARY_2004", "감정 일기 삭제가 완료되었습니다."),
    REDIARY_TODAY_WRITTEN_CHECKD(HttpStatus.OK, "REDIARY_2005", "당일 감정일기 작성한 여부 알려줍니다."),

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