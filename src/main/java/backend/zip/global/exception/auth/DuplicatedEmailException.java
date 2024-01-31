package backend.zip.global.exception.auth;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class DuplicatedEmailException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public DuplicatedEmailException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
