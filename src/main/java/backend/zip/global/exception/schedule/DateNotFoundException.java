package backend.zip.global.exception.schedule;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class DateNotFoundException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public DateNotFoundException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
