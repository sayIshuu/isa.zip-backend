package backend.zip.service.broker;

import backend.zip.domain.broker.BrokerItem;

public interface BrokerItemAddressService {
    BrokerItem saveBrokerItemAddress(Long userId,String address,String roadAddress, String dong,String roadDong,String postNumber, Double x, Double y);

//    public BrokerItem updateBrokerItemAddress(Long brokerItemId, String addressName, String dong, Double x, Double y);

//    BrokerItem saveBrokerItemAddress(Long userId, String address, String dong, Double x, Double y);

    BrokerItem updateBrokerItemAddress(Long brokerItemId, String address, String roadAddress, String dong, String roadDong, String postNumber, Double x, Double y);
}
