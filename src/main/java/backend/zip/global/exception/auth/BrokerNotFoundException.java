package backend.zip.global.exception.auth;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class BrokerNotFoundException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public BrokerNotFoundException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
