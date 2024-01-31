package backend.zip.global.exception.auth;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class UnableToSendEmailException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public UnableToSendEmailException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
