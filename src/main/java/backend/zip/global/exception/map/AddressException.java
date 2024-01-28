package backend.zip.global.exception.map;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class AddressException extends GeneralException {
    public AddressException(BaseErrorCode code) {
        super(code);
    }
}
