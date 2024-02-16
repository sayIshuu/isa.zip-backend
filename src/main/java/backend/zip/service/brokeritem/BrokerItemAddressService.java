package backend.zip.service.brokeritem;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.dto.brokeritem.response.BrokerItemAddressResponse;

public interface BrokerItemAddressService {
    BrokerItem saveBrokerItemAddress(Long userId, BrokerItemAddressResponse brokerItemAddressResponse);
    BrokerItem updateBrokerItemAddress(Long brokerItemId, String address, String roadAddress, String dong, String roadDong, String postNumber, Double x, Double y);
}
