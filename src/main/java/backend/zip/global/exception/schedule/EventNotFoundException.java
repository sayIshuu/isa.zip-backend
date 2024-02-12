package backend.zip.global.exception.schedule;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class EventNotFoundException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public EventNotFoundException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
