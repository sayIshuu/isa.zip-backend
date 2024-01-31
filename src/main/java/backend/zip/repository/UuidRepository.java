package backend.zip.repository;

import backend.zip.domain.s3.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UuidRepository extends JpaRepository<Uuid, Long> {
    Uuid findByUuid(String uuid);
}