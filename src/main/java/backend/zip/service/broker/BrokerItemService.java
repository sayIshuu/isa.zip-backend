package backend.zip.service.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.dto.brokeritem.request.AddBrokerItemDetailsRequest;
import backend.zip.dto.brokeritem.request.AddBrokerItemOptionsRequest;
import backend.zip.dto.brokeritem.response.BrokerItemAddressResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BrokerItemService {
    public BrokerItem saveBrokerItem(Long userId, BrokerItem brokerItem, BrokerItemAddressResponse brokerItemAddressResponse,
                                     AddBrokerItemDetailsRequest detailsRequest, MultipartFile[] brokerItemImg, AddBrokerItemOptionsRequest optionsRequest);

    public void deleteBrokerItem(Long brokerItemId);

//    public BrokerItem findBrokerItem(Long brokerItemId);

//    public List<BrokerItem> findAllBrokerItem(Long userId);

    @Transactional
    public BrokerItem updateBrokerItem(Long brokerItemId, String roadFullAddress,
                                AddBrokerItemDetailsRequest detailsRequest,
                                AddBrokerItemOptionsRequest optionsRequest, MultipartFile[] multipartFiles);
}
