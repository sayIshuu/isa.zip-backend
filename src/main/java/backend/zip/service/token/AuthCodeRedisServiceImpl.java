package backend.zip.service.token;

import backend.zip.domain.auth.AuthCode;
import backend.zip.repository.auth.AuthCodeRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthCodeRedisServiceImpl implements AuthCodeRedisService {

    private final AuthCodeRedisRepository authCodeRedisRepository;

    @Value("${spring.mail.properties.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    @Override
    @Transactional
    public void setCode(String id, String code) {
        authCodeRedisRepository.save(AuthCode.builder()
                .id(id)
                .code(code)
                .ttl(authCodeExpirationMillis)
                .build());
    }

    @Override
    public AuthCode getCode(String id) {
        return authCodeRedisRepository.findById(id)
                .orElse(null);
    }
}
