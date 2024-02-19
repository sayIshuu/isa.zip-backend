package backend.zip.global.exception.auth;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class CustomNoSuchAlgorithmException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public CustomNoSuchAlgorithmException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
