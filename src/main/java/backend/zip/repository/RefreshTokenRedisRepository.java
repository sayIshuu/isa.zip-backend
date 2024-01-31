package backend.zip.repository;

import backend.zip.domain.auth.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, Long> {
}
