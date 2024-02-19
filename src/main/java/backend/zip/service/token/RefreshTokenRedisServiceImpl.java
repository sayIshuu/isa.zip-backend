package backend.zip.service.token;

import backend.zip.domain.auth.RefreshToken;
import backend.zip.repository.token.RefreshTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenRedisServiceImpl implements RefreshTokenRedisService {
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    @Value("${jwt.refresh-expired-in}")
    private long REFRESH_TOKEN_EXPIRED_IN;
    @Override
    @Transactional
    public void saveRefreshToken(Long id, String refreshToken) {
        refreshTokenRedisRepository.save(RefreshToken.builder()
                .id(id)
                .refreshToken(refreshToken)
                .ttl(REFRESH_TOKEN_EXPIRED_IN)
                .build());
    }
    @Override
    public RefreshToken getRefreshToken(Long id) {
        return refreshTokenRedisRepository.findById(id)
                .orElse(null);
    }

    @Override
    public void deleteRefreshToken(Long id) {
        refreshTokenRedisRepository.delete(
                RefreshToken.builder().id(id).build()
        );
    }
}
