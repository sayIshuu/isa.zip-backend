package backend.zip.global.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ReasonDto {
    private HttpStatus httpStatus;

    private final boolean isSuccess;
    private final String code;
    private final String message;
}
