package backend.zip.global.exception.token;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class TokenNotFoundException extends TokenException {
    private BaseErrorCode baseErrorCode;
    public TokenNotFoundException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
