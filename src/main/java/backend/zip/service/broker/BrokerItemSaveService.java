package backend.zip.service.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.dto.brokeritem.request.AddBrokerItemOptionsRequest;

public interface BrokerItemSaveService {
    public BrokerItem saveBrokerItem(Long userId, String address, String dong, Double x, Double y, AddBrokerItemOptionsRequest optionsRequest);
}
