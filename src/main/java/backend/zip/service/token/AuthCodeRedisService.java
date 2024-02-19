package backend.zip.service.token;

import backend.zip.domain.auth.AuthCode;

public interface AuthCodeRedisService {
    void setCode(String id, String code);
    AuthCode getCode(String id);
}
