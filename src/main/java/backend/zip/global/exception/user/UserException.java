package backend.zip.global.exception.user;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class UserException extends GeneralException {
    public UserException(BaseErrorCode code) {
        super(code);
    }
}
