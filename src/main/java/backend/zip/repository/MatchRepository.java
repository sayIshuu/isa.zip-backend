package backend.zip.repository;

import backend.zip.domain.enums.MatchStatus;
import backend.zip.domain.match.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Matching, Long> {

    // userId가 같은 userItem을 가지고 있고 matchStatus가 같은 Matching 객체들을 반환
    // 양방향 참조 상태라 이런 쿼리문 가능
    @Query("select m from Matching m where m.userItem.user.id = :userId and m.matchStatus = :matchStatus")
    List<Matching> findByUserItemUserIdAndMatchStatus(Long userId, MatchStatus matchStatus);


    @Query("select m from Matching m join fetch m.userItem ui join fetch ui.userOption uo " +
            "join fetch m.brokerItem bi join fetch bi.itemContent join fetch bi.itemImages join fetch bi.brokerOption " +
            "join fetch bi.user u join fetch u.broker ub ")
    List<Matching> findAll();

}
