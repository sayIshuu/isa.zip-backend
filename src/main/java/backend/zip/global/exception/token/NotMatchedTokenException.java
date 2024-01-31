package backend.zip.global.exception.token;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class NotMatchedTokenException extends TokenException {
    private BaseErrorCode baseErrorCode;
    public NotMatchedTokenException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
