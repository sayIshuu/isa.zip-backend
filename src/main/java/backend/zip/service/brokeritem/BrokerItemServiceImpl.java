package backend.zip.service.brokeritem;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.enums.ItemStatus;
import backend.zip.domain.item.ItemContent;
import backend.zip.domain.item.ItemImage;
import backend.zip.dto.brokeritem.request.AddBrokerItemDetailsRequest;
import backend.zip.dto.brokeritem.request.AddBrokerItemOptionsRequest;
import backend.zip.dto.brokeritem.response.BrokerItemAddressResponse;
import backend.zip.global.exception.brokeritem.BrokerItemException;
import backend.zip.global.status.ErrorStatus;
import backend.zip.repository.UserRepository;
import backend.zip.repository.broker.BrokerItemRepository;
import backend.zip.repository.broker.BrokerOptionRepository;
import backend.zip.service.map.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrokerItemServiceImpl implements BrokerItemService {
    private final BrokerItemRepository brokerItemRepository;
    private final BrokerOptionRepository brokerOptionRepository;
    private final UserRepository userRepository;
    private final BrokerItemAddressService brokerItemAddressService;
    private final BrokerItemDetailService brokerItemDetailService;
    private final BrokerItemOptionService brokerItemOptionService;
    private final AddressService addressService;


    @Transactional
    @Override
    public BrokerItem saveBrokerItem(Long userId, BrokerItem brokerItem, BrokerItemAddressResponse addressResponse,
                                     AddBrokerItemDetailsRequest detailsRequest,
                                     MultipartFile[] brokerItemImg,
                                     AddBrokerItemOptionsRequest optionsRequest) {

//        brokerItem = brokerItemAddressService.saveBrokerItemAddress(userId, addressResponse.getAddressName(), addressResponse.getRoadName(),
//                addressResponse.getDong(), addressResponse.getRoadDong(), addressResponse.getPostNumber(), addressResponse.getX(), addressResponse.getY());

        brokerItem.setItemStatus(ItemStatus.ITEM_SELLING);
        ItemContent itemContent = brokerItemDetailService.saveBrokerItemContentDetails(detailsRequest, brokerItem);
        List<ItemImage> itemImages = brokerItemDetailService.saveBrokerItemImageDetails(brokerItemImg, brokerItem);
        brokerItem.setDetails(itemImages, itemContent);

        BrokerOption brokerOption = brokerItemOptionService.saveBrokerItemOptions(optionsRequest);
        brokerItem.setBrokerOption(brokerOption);

        return brokerItemRepository.save(brokerItem);
    }

    @Override
    public void deleteBrokerItem(Long brokerItemId) {
        brokerItemRepository.deleteById(brokerItemId);
    }

    @Override
    @Transactional
    public BrokerItem updateBrokerItem(Long brokerItemId, String roadFullAddress,
                                       AddBrokerItemDetailsRequest detailsRequest,
                                       AddBrokerItemOptionsRequest optionsRequest, MultipartFile[] multipartFiles) {

        BrokerItem brokerItem = brokerItemRepository.findById(brokerItemId)
                .orElseThrow(() -> new BrokerItemException(ErrorStatus.BROKER_ITEM_NOT_FOUND));

        if (roadFullAddress != null) {
            updateAddress(brokerItem, roadFullAddress);
        } if (detailsRequest != null) {
            updateItemContent(brokerItem, detailsRequest);
        } if (multipartFiles != null && multipartFiles.length > 0) {
            updateItemImages(brokerItem, multipartFiles);
        } if (optionsRequest != null) {
            updateOptions(brokerItem, optionsRequest);
        }
//        return brokerItemRepository.save(brokerItem);
        return brokerItem;
    }


    private void updateAddress(BrokerItem brokerItem, String roadFullAddress) {
        String kaKaoApiFromInputAddress = addressService.getKaKaoApiFromInputAddress(roadFullAddress);
        BrokerItemAddressResponse addressResponse = addressService.returnAddressInfo(kaKaoApiFromInputAddress);

        brokerItemAddressService.updateBrokerItemAddress(brokerItem.getBrokerItemId(),
                addressResponse.getAddressName(),addressResponse.getRoadName(), addressResponse.getDong(),addressResponse.getRoadDong(),
                addressResponse.getPostNumber(),
                addressResponse.getX(), addressResponse.getY());
    }

    private void updateItemContent(BrokerItem brokerItem, AddBrokerItemDetailsRequest detailsRequest) {
        ItemContent itemContent = brokerItemDetailService.updateBrokerItemContent(brokerItem.getBrokerItemId(), detailsRequest);
        brokerItem.updateItemContent(itemContent);
    }

    private void updateItemImages(BrokerItem brokerItem, MultipartFile[] multipartFiles) {
        List<ItemImage> itemImages = brokerItemDetailService.updateBrokerItemImages(brokerItem.getBrokerItemId(), multipartFiles);
        brokerItem.updateItemImages(itemImages);
    }


    private void updateOptions(BrokerItem brokerItem, AddBrokerItemOptionsRequest optionsRequest) {
        BrokerOption brokerOption = brokerItemOptionService.updateBrokerItemOptions(brokerItem.getBrokerItemId(), optionsRequest);
        brokerItem.updateOptions(brokerOption);
    }

    @Override
    @Transactional
    public BrokerItem makeBrokerItemSoldOut(Long brokerItemId) {
        BrokerItem brokerItem = brokerItemRepository.findById(brokerItemId)
                .orElseThrow(() -> new BrokerItemException(ErrorStatus.BROKER_ITEM_NOT_FOUND));

        brokerItem.setItemStatus(ItemStatus.ITEM_SOLD_OUT);
        return brokerItemRepository.save(brokerItem);
    }
}
