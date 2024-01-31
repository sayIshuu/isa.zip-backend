package backend.zip.service.broker;

import backend.zip.domain.broker.BrokerOption;
import backend.zip.dto.brokeritem.request.AddBrokerItemOptionsRequest;

public interface BrokerItemOptionService {
    public BrokerOption saveBrokerItemOptions(AddBrokerItemOptionsRequest addBrokerItemOptionsRequest);
}
