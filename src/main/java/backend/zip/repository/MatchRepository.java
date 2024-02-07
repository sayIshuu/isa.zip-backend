package backend.zip.repository;

import backend.zip.domain.match.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Matching, Long> {

}
