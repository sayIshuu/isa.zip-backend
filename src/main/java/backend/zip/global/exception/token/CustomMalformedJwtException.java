package backend.zip.global.exception.token;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class CustomMalformedJwtException extends TokenException {
    private BaseErrorCode baseErrorCode;
    public CustomMalformedJwtException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
