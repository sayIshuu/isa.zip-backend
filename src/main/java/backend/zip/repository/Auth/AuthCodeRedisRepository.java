package backend.zip.repository.Auth;

import backend.zip.domain.auth.AuthCode;
import org.springframework.data.repository.CrudRepository;

public interface AuthCodeRedisRepository extends CrudRepository<AuthCode, String> {
}