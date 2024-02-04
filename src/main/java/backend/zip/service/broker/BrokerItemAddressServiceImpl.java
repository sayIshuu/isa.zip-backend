package backend.zip.service.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.user.User;
import backend.zip.dto.brokeritem.request.AddBrokerItemAddressRequest;
import backend.zip.global.exception.brokeritem.BrokerItemException;
import backend.zip.global.status.ErrorStatus;
import backend.zip.repository.broker.BrokerItemRepository;
import backend.zip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrokerItemAddressServiceImpl implements BrokerItemAddressService {

    private final BrokerItemRepository brokerItemRepository;
    private final UserRepository userRepository;

    @Override
    public BrokerItem saveBrokerItemAddress(Long userId, String address, String dong, Double x, Double y) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BrokerItemException(ErrorStatus.USER_NOT_FOUND)); // 여기서 수정
        BrokerItem brokerItem = new AddBrokerItemAddressRequest().toEntity(user, address, dong, x, y);
//        BrokerItem savedBrokerItemByAddress = brokerItemRepository.save(brokerItem);
        return brokerItem;
    }

    @Override
    public BrokerItem updateBrokerItemAddress(Long brokerItemId, String address,String roadAddress, String dong,String roadDong,String postNumber,Double x, Double y) {
        BrokerItem brokerItem = brokerItemRepository.findById(brokerItemId)
                .orElseThrow(() -> new BrokerItemException(ErrorStatus.BROKER_ITEM_NOT_FOUND));

        brokerItem.updateAddressDetail(address, roadAddress, dong, roadDong,postNumber, x, y);
        return brokerItem;
    }
}
