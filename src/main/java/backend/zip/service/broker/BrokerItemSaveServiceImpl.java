package backend.zip.service.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.user.User;
import backend.zip.dto.brokeritem.request.AddBrokerItemAddressRequest;
import backend.zip.dto.brokeritem.request.AddBrokerItemOptionsRequest;
import backend.zip.global.exception.brokeritem.BrokerItemException;
import backend.zip.global.status.ErrorStatus;
import backend.zip.repository.UserRepository;
import backend.zip.repository.broker.BrokerItemRepository;
import backend.zip.repository.broker.BrokerOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrokerItemSaveServiceImpl implements BrokerItemSaveService {
    private final BrokerItemRepository brokerItemRepository;
    private final BrokerOptionRepository brokerOptionRepository;
    private final UserRepository userRepository;
    private final BrokerItemAddressService brokerItemAddressService;
    private final BrokerItemOptionService brokerItemOptionService;


    @Transactional
    @Override
    public BrokerItem saveBrokerItem(Long userId, String address, String dong, Double x, Double y, AddBrokerItemOptionsRequest optionsRequest) {
        // 주소 저장 로직
        BrokerItem brokerItem = brokerItemAddressService.saveBrokerItemAddress(userId, address, dong, x, y);

        // 옵션 저장 로직
        BrokerOption brokerOption = brokerItemOptionService.saveBrokerItemOptions(brokerItem.getBrokerItemId(), optionsRequest);

        // BrokerItem과 BrokerOption 연결
        brokerItem.setBrokerOption(brokerOption);
        brokerItemRepository.save(brokerItem);

        return brokerItem;
    }
}
