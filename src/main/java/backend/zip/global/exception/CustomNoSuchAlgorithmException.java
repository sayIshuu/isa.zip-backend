package backend.zip.global.exception;

import backend.zip.global.code.BaseErrorCode;

public class CustomNoSuchAlgorithmException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public CustomNoSuchAlgorithmException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
