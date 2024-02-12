package backend.zip.global.exception.schedule;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class DuplicatedScheduleException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public DuplicatedScheduleException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}