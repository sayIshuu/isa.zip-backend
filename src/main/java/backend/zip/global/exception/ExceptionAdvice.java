package backend.zip.global.exception;

import backend.zip.global.apipayload.ApiResponse;
import backend.zip.global.dto.ErrorReasonDto;
import backend.zip.global.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    /**
     * 요청된 타입과 맞지 않는 파라미터가 전달될 때 발생
     * <p>
     * 작동 방식: 예외에서 속성 이름을 추출하고, "올바른 값이 아닙니다."라는 메시지와 함께
     * 내부 메소드 handleExceptionInternalMessage로 전달
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers,
                                                        HttpStatusCode status, WebRequest request) {

        String errorMessage = e.getPropertyName() + ": 올바른 값이 아닙니다.";

        return handleExceptionInternalMessage(e, headers, request, errorMessage);
    }

    /**
     * MissingServletRequestParameterException을 처리
     * 이 예외는 필수 요청 파라미터가 누락 되었을 때 발생
     * <p>
     * 작동 방식: 누락된 파라미터 이름을 추출하고, 동일한 "올바른 값이 아닙니다." 메시지를 사용하여 처리
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e,
                                                                          HttpHeaders headers,
                                                                          HttpStatusCode status, WebRequest webRequest) {

        String errorMessage = e.getParameterName() + ": 올바른 값이 아닙니다.";

        return handleExceptionInternalMessage(e, headers, webRequest, errorMessage);
    }

    /**
     * MethodArgumentNotValidException을 처리
     * 이 예외는 메소드 파라미터의 유효성 검사 실패 시 발생
     * <p>
     * 작동 방식: 각 필드 오류에 대한 정보를 수집하고, 이를 ErrorStatus._BAD_REQUEST와 함께
     * handleExceptionInternalArgs 메소드로 전달
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers,
                                                               HttpStatusCode status, WebRequest webRequest) {

        Map<String, String> errors = new LinkedHashMap<>();

        e.getBindingResult().getFieldErrors().stream()
                .forEach(fieldError -> {
                            String fieldName = fieldError.getField();
                            String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");

                            errors.merge(
                                    fieldName,
                                    errorMessage,
                                    (existingErrorMessage, newErrorMessage) ->
                                            existingErrorMessage + ", " + newErrorMessage);
                        }
                );

        return handleExceptionInternalArgs(e, HttpHeaders.EMPTY, ErrorStatus.valueOf("_BAD_REQUEST"),
                webRequest, errors);
    }

    /**
     * ConstraintViolationException을 처리
     * 이 예외는 Bean Validation API에 의해 유효성 검사 실패 시 발생
     *
     * 작동 방식: 유효성 검사 위반에 대한 메시지를 수집하고, ErrorStatus.valueOf를 사용하여 적절한 에러 상태(String)로 변환 후,
     * handleExceptionInternalConstraint 메소드로 처리
     */
    @ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest webRequest) {
        String errorMessage =
                e.getConstraintViolations().stream()
                        .map(constraintViolation -> constraintViolation.getMessage())
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "ConstraintViolationException 추출 도중 에러 발생"));

        return handleExceptionInternalConstraint(e, ErrorStatus.valueOf(errorMessage), HttpHeaders.EMPTY, webRequest);
    }

    /**
     * 일반 Exception을 처리
     * 이 메소드는 처리되지 않은 다른 모든 예외를 캡처함
     *
     * 작동 방식: 예외의 메시지와 ErrorStatus._INTERNAL_SERVER_ERROR를 사용하여
     * handleExceptionInternalFalse 메소드로 처리합니다.
     */
    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest webRequest) {
        e.printStackTrace();

        return handleExceptionInternalFalse(e, ErrorStatus._INTERNAL_SERVER_ERROR, HttpHeaders.EMPTY,
                ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus(), webRequest, e.getMessage());
    }

    /**
     * 사용자 정의 예외 GeneralException을 처리함
     *
     * 작동 방식: GeneralException에서 ErrorReasonDto를 추출하고,
     * 이를 handleExceptionInternal 메소드로 전달함
     */
    @ExceptionHandler(value = GeneralException.class)
    public ResponseEntity onThrowException(GeneralException generalException, HttpServletRequest httpServletRequest) {

        ErrorReasonDto errorReasonHttpStatus = generalException.getErrorReasonHttpStatus();

        return handleExceptionInternal(generalException, errorReasonHttpStatus, null, httpServletRequest);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorReasonDto reason,
                                                           HttpHeaders httpHeaders,
                                                           HttpServletRequest httpServletRequest) {

        ApiResponse<Object> body = ApiResponse.onFailure(reason.getCode(), reason.getMessage(), null);
        WebRequest webRequest = new ServletWebRequest(httpServletRequest);

        return super.handleExceptionInternal(e, body, httpHeaders, reason.getHttpStatus(), webRequest);
    }

    private ResponseEntity<Object> handleExceptionInternalArgs(Exception e, HttpHeaders httpHeaders,
                                                               ErrorStatus errorStatus,
                                                               WebRequest webRequest,
                                                               Map<String, String> errorArgs) {

        ApiResponse<Object> body = ApiResponse.onFailure(errorStatus.getCode(), errorStatus.getMessage(), null);

        return super.handleExceptionInternal(e, body, httpHeaders, errorStatus.getHttpStatus(), webRequest);
    }


    private ResponseEntity<Object> handleExceptionInternalMessage(Exception e, HttpHeaders httpHeaders,
                                                                  WebRequest webrequest, String errorMessage) {

        ErrorStatus badRequestErrorStatus = ErrorStatus._BAD_REQUEST;
        ApiResponse<String> body = ApiResponse.onFailure(badRequestErrorStatus.getCode(), badRequestErrorStatus.getMessage(), errorMessage);

        return super.handleExceptionInternal(e, body, httpHeaders, badRequestErrorStatus.getHttpStatus(), webrequest);
    }

    private ResponseEntity<Object> handleExceptionInternalConstraint(Exception e, ErrorStatus errorStatus, HttpHeaders headers, WebRequest request) {
        ApiResponse<Object> body = ApiResponse.onFailure(errorStatus.getCode(), errorStatus.getMessage(), null);

        return super.handleExceptionInternal(e, body, headers, errorStatus.getHttpStatus(), request);
    }

    private ResponseEntity<Object> handleExceptionInternalFalse(Exception e, ErrorStatus errorStatus,
                                                                HttpHeaders httpHeaders, HttpStatus httpStatus,
                                                                WebRequest webRequest, String errorPoint) {

        ApiResponse<String> body = ApiResponse.onFailure(errorStatus.getCode(), errorStatus.getMessage(), errorPoint);

        return super.handleExceptionInternal(e, body, httpHeaders, httpStatus, webRequest);
    }
}

