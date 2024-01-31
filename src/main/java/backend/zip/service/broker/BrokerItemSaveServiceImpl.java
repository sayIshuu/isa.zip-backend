package backend.zip.service.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.item.ItemContent;
import backend.zip.domain.item.ItemImage;
import backend.zip.domain.user.User;
import backend.zip.dto.brokeritem.request.AddBrokerItemAddressRequest;
import backend.zip.dto.brokeritem.request.AddBrokerItemDetailsRequest;
import backend.zip.dto.brokeritem.request.AddBrokerItemOptionsRequest;
import backend.zip.dto.brokeritem.response.BrokerItemDetailResponse;
import backend.zip.global.exception.brokeritem.BrokerItemException;
import backend.zip.global.status.ErrorStatus;
import backend.zip.repository.UserRepository;
import backend.zip.repository.broker.BrokerItemRepository;
import backend.zip.repository.broker.BrokerOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrokerItemSaveServiceImpl implements BrokerItemSaveService {
    private final BrokerItemRepository brokerItemRepository;
    private final BrokerOptionRepository brokerOptionRepository;
    private final BrokerItemAddressService brokerItemAddressService;
    private final BrokerItemDetailService brokerItemDetailService;
    private final BrokerItemOptionService brokerItemOptionService;


    @Transactional
    @Override
    public BrokerItem saveBrokerItem(Long userId,BrokerItem brokerItem, String address, String dong, Double x, Double y,
                                     AddBrokerItemDetailsRequest detailsRequest,
                                     MultipartFile[] brokerItemImg,
                                     AddBrokerItemOptionsRequest optionsRequest) {

        // 주소 저장 로직
        brokerItem = brokerItemAddressService.saveBrokerItemAddress(userId,address, dong, x, y);

        //사진, 간단 소개, 상세 설명 저장 로직
        ItemContent itemContent = brokerItemDetailService.saveBrokerItemContentDetails(detailsRequest,brokerItem);
        List<ItemImage> itemImages = brokerItemDetailService.saveBrokerItemImageDetails(brokerItemImg,brokerItem);
        // BrokerItem과 ItemContent, ItemImage 연결
        brokerItem.setDetails(itemImages,itemContent);


        // 옵션 저장 로직
        BrokerOption brokerOption = brokerItemOptionService.saveBrokerItemOptions(optionsRequest);
        // BrokerItem과 BrokerOption 연결
        brokerItem.setBrokerOption(brokerOption);

        brokerItemRepository.save(brokerItem);

        return brokerItem;
    }
}
