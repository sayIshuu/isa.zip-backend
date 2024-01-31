package backend.zip.global.status;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.dto.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    //기본(전역) 에러
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COMMON_500", "서버에서 요청을 처리 하는 동안 오류가 발생했습니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON_400", "입력 값이 잘못된 요청 입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON_401", "인증이 필요 합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_403", "금지된 요청 입니다."),

    //User 관련 에러
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_001", "존재 하지 않는 사용자 입니다."),

    // Auth 관련 에러
    NO_SUCH_ALGORITHM(HttpStatus.INTERNAL_SERVER_ERROR, "NO_SUCH_ALGORITHM", "알 수 없는 알고리즘입니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "DUPLICATED_EMAIL", "존재하는 이메일 주소입니다."),
    UNABLE_TO_SEND_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "UNABLE_TO_SEND_EMAIL", "이메일을 보낼 수 없습니다."),

    //Address 관련 에러
//    ADDRESS_IO_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "ADDRESS_001", "입출력 처리 중 오류가 발생했습니다."),
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "ADDRESS_001", "주소를 찾을 수 없습니다."),

    //BrokerItem 관련 에러
    BROKER_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND,"BROKER_ITEM_001","해당 매물을 찾을 수 없습니다");




    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDto getReason() {
        return ErrorReasonDto.builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }

    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
                .isSuccess(false)
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .build();
    }


}
