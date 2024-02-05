package backend.zip.service.broker;

import backend.zip.domain.broker.BrokerItem;

import java.util.List;

public interface BrokerItemShowService {
    List<BrokerItem> findBrokerItemList(Long userId);

    BrokerItem findBrokerItem(Long brokerItemId);
}
