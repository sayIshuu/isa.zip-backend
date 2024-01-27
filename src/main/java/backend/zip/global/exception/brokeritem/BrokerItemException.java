package backend.zip.global.exception.brokeritem;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class BrokerItemException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public BrokerItemException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
