package backend.zip.global.exception.token;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class TokenException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public TokenException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
