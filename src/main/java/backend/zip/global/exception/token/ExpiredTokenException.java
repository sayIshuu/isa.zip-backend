package backend.zip.global.exception.token;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class ExpiredTokenException extends TokenException {
    private BaseErrorCode baseErrorCode;
    public ExpiredTokenException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
