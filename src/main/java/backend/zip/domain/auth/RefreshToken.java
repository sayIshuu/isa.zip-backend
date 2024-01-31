package backend.zip.domain.auth;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refresh_token")
public class RefreshToken {
    @Id
    private Long id;

    private String refreshToken;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long ttl;

    @Builder
    public RefreshToken(Long id, String refreshToken, Long ttl) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.ttl = ttl;
    }
}
