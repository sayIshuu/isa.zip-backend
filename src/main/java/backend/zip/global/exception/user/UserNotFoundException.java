package backend.zip.global.exception.user;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class UserNotFoundException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public UserNotFoundException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
