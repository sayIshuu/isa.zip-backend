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
@RedisHash(value = "auth_code")
public class AuthCode {
    @Id
    private String id;

    private String code;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long ttl;

    @Builder
    public AuthCode(String id, String code, Long ttl) {
        this.id = id;
        this.code = code;
        this.ttl = ttl;
    }
}
