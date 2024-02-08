package backend.zip.global.exception.match;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class MatchingException extends GeneralException {
    public MatchingException(BaseErrorCode code) {
        super(code);
    }
}
