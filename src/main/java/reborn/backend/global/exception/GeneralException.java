package reborn.backend.global.exception;

import reborn.backend.global.api_payload.ErrorCode;
import reborn.backend.global.api_payload.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private final ErrorCode code;

    public static GeneralException of(ErrorCode code) {
        return new GeneralException(code);
    }

    public ReasonDto getReason() {
        return this.code.getReason();
    }

}
