package backend.zip.repository.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BrokerItemRepository extends JpaRepository<BrokerItem, Long> {
    //UserID(BrokerID)에 따른 매물 조회
//    List<BrokerItem> findAllByUserId(Long userId);

//    //동에 따른 전체 매물 요청 조회
//    List<BrokerItem> findByDong(String dong);

    Optional<BrokerItem> findById(Long brokerItemId);

//    @Query("SELECT b FROM BrokerItem b WHERE b.user.id = :userId AND b.id = :brokerItemId")
//    Optional<BrokerItem> findBrokerItemByUserIdAndBrokerItemId(@Param("userId") Long userId, @Param("brokerItemId") Long brokerItemId);

//    List<BrokerItem> findBrokerItemByUser(Long userId);

    @Query("select b from BrokerItem b where b.user.id = :userId")
    List<BrokerItem> findBrokerItemByUser(@Param("userId") Long userId);


//    @Query("select b from BrokerItem b where b.user.id = :userId")
//    List<BrokerItem> findBrokerItemByUser(@Param("userId") Long userId);


}
