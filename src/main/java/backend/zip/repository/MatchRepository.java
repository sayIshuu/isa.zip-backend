package backend.zip.repository;

import backend.zip.domain.enums.MatchStatus;
import backend.zip.domain.match.Matching;
import backend.zip.domain.user.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Matching, Long> {

    // Fetch Join이용해서 userId가 같은 userItem을 가지고 있고 matchStatus가 같은 Matching 객체들을 반환
    //@Query("select m from Matching m where m.userItem.user.id = :userId and m.matchStatus = :matchStatus")
    @Query("select m from Matching m join fetch m.userItem ui join fetch ui.user" +
            " where ui.user.id = :userId and m.matchStatus = :matchStatus")
    List<Matching> findByUserItemUserIdAndMatchStatus(Long userId, MatchStatus matchStatus);

    @Query("select m from Matching m " +
            "join fetch m.userItem ui " +
            "join fetch ui.userOption uo " +
            "join fetch m.brokerItem bi " +
            "join fetch bi.itemContent " +
            "join fetch bi.itemImages " +
            "join fetch bi.brokerOption " +
            "join fetch bi.user u " +
            "join fetch u.broker ub " +
            "where u.id = :userId")
    List<Matching> findAll(@Param("userId") Long userId);

  List<Matching> findByUserItem(UserItem userItem);

  @Modifying
  @Query("delete from Matching m where m.userItem = :userItem and m.matchStatus = :matchStatus")
  void deleteByUserItemAndMatchStatus(@Param("userItem") UserItem userItem, @Param("matchStatus") MatchStatus matchStatus);

}
