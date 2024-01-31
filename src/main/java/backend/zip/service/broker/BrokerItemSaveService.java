package backend.zip.service.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.dto.brokeritem.request.AddBrokerItemDetailsRequest;
import backend.zip.dto.brokeritem.request.AddBrokerItemOptionsRequest;
import org.springframework.web.multipart.MultipartFile;

public interface BrokerItemSaveService {
    public BrokerItem saveBrokerItem(Long userId, BrokerItem brokerItem, String address, String dong, Double x, Double y,
                                     AddBrokerItemDetailsRequest detailsRequest, MultipartFile[] brokerItemImg, AddBrokerItemOptionsRequest optionsRequest);
}
