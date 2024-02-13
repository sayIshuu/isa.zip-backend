package backend.zip.repository.broker;

import backend.zip.domain.broker.BrokerItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BrokerItemRepository extends JpaRepository<BrokerItem, Long> {
    @Query("select b from BrokerItem b " +
            "join fetch b.brokerOption bo " +
            "join fetch  b.itemContent " +
            "join fetch b.itemImages " +
            "join fetch b.user u " +
            "left join fetch u.broker br " +
            "where b.brokerItemId = :brokerItemId")
    Optional<BrokerItem> findById(Long brokerItemId);



//    @Query("select b from BrokerItem b " +
//        "join fetch b.brokerOption bo " +
//        "join fetch b.itemContent " +
//        "join fetch b.itemImages  " +
//        "where b.user.id = :userId")
    @Query("select b from BrokerItem b " +
            "join fetch b.brokerOption bo " +
            "join fetch  b.itemContent " +
            "join fetch b.itemImages " +
            "join fetch b.user u " +
            "left join fetch u.broker br " +
            "where u.id = :userId")
    List<BrokerItem> findBrokerItemByUser(@Param("userId") Long userId);

    @Query("select b from BrokerItem b where b.dong = :dong")
    List<BrokerItem> findAllByDong(@Param("dong") String dong);

}
