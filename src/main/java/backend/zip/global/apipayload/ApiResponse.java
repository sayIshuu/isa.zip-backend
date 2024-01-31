package backend.zip.global.apipayload;

import backend.zip.global.code.BaseCode;
import backend.zip.global.status.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    public static <T> ApiResponse<T> onSuccess(T data) {
        return new ApiResponse<>(
                true, SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), data);
    }

    public static <T> ApiResponse<T> onSuccess(String message, T data) {
        return new ApiResponse<>(true, SuccessStatus._OK.getCode(), message, data);
    }

    public static <T> ApiResponse<T> onSuccess(BaseCode code, T data) {
        return new ApiResponse<>(true, code.getReasonHttpStatus().getCode(),
                code.getReasonHttpStatus().getMessage(), data);
    }

    public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
        return new ApiResponse<>(false, code, message, data);
    }
}
