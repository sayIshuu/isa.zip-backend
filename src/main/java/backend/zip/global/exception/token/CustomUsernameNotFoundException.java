package backend.zip.global.exception.token;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class CustomUsernameNotFoundException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public CustomUsernameNotFoundException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
