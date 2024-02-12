package backend.zip.global.exception.schedule;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class ScheduleNotFoundException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public ScheduleNotFoundException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
