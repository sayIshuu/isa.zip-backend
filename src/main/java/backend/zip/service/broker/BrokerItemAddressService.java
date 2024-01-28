package backend.zip.service.broker;

import backend.zip.domain.broker.BrokerItem;

public interface BrokerItemAddressService {
    public BrokerItem saveBrokerItemAddress(Long userId,String address, String dong, Double x, Double y);

}
