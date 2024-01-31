package backend.zip.global.exception.auth;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class AuthcodeException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public AuthcodeException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
