package backend.zip.repository;

import backend.zip.domain.broker.Broker;
import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.enums.ItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface BrokerItemRepository extends JpaRepository<BrokerItem, Long> {
    //UserID(BrokerID)에 따른 매물 조회
    List<BrokerItem> findAllByUserId(Long userId);

    //매물 상세 조회
    Optional<BrokerItem> findBrokerItemByBrokerItemId(Long brokerItemId);

    //지역에 따른 요청 조회 -> '동' 처리를 다시 해야함
    List<BrokerItem> findBrokerItemByAddress(String address);

}
