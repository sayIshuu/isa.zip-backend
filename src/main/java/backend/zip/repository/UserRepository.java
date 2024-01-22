package backend.zip.repository;

import backend.zip.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    User findByNickName(String nickName);
    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);

}
