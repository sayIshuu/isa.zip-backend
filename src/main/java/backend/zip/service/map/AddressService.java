package backend.zip.service.map;

import backend.zip.dto.brokeritem.response.BrokerItemAddressResponse;

import java.util.HashMap;
import java.util.List;

public interface AddressService {
    public String getKaKaoApiFromInputAddress(String roadFullAddress);
    public HashMap<String, String> getXYMapFromJson(String jsonString);

    public BrokerItemAddressResponse returnAddressInfo(String jsonString);

    //public UserItemAddressResponse returnUserItemAddressAndDong(String jsonString);
}
