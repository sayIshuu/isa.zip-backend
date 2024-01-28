package backend.zip.service.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.dto.brokeritem.request.AddBrokerItemDetailsRequest;
import org.springframework.web.multipart.MultipartFile;

public interface BrokerItemDetailService {
    public BrokerItem saveBrokerItemDetails(Long userId, AddBrokerItemDetailsRequest addBrokerItemDetailsRequest, MultipartFile[] multipartFiles);

}
