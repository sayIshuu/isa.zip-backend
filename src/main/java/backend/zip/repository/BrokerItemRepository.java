package backend.zip.repository;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrokerItemRepository extends JpaRepository<BrokerItem, Long> {
    //UserID(BrokerID)에 따른 매물 조회
    List<BrokerItem> findAllByUser(User user);

//    //지역에 따른 요청 조회 -> '동' 처리를 다시 해야함
//    List<BrokerItem> findBrokerItemByAddress(String address);

}
