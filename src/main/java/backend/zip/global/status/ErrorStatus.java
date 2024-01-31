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

    //Address 관련 에러
//    ADDRESS_IO_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "ADDRESS_001", "입출력 처리 중 오류가 발생했습니다."),
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "ADDRESS_001", "주소를 찾을 수 없습니다."),

    //이미지 관련 에러
    ERROR_AT_AMAZONS3_MANAGER_UPLOADFILE(HttpStatus.INTERNAL_SERVER_ERROR,"IMAGE_001","이미지를 첨부하는 동안 오류가 발생했습니다"),

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
