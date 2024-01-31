package backend.zip.service;

import backend.zip.domain.auth.RefreshToken;

public interface RefreshTokenRedisService {
    void saveRefreshToken(Long id, String refreshToken);
    RefreshToken getRefreshToken(Long id);

    void deleteRefreshToken(Long id);
}
