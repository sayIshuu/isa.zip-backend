package backend.zip.service.brokeritem;

import backend.zip.domain.broker.BrokerItem;

import java.util.List;
import java.util.Set;

public interface BrokerItemShowService {
    List<BrokerItem> findBrokerItemList(Long userId);

    BrokerItem findBrokerItem(Long brokerItemId);

    Long checkBroker();

    Set<String> findDongOfBrokerItem(Long userId);

}
