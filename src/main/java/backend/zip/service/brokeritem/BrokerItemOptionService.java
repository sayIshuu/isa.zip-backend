package backend.zip.service.brokeritem;

import backend.zip.domain.broker.BrokerOption;
import backend.zip.dto.brokeritem.request.AddBrokerItemOptionsRequest;

public interface BrokerItemOptionService {
    public BrokerOption saveBrokerItemOptions(AddBrokerItemOptionsRequest addBrokerItemOptionsRequest);
    public BrokerOption updateBrokerItemOptions(Long brokerItemId, AddBrokerItemOptionsRequest optionsRequest);

}
