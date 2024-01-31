package backend.zip.global.exception.image;

import backend.zip.global.code.BaseErrorCode;
import backend.zip.global.exception.GeneralException;

public class ImageException extends GeneralException {
    private BaseErrorCode baseErrorCode;
    public ImageException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
