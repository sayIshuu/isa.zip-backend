package backend.zip.service.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.global.exception.brokeritem.BrokerItemException;
import backend.zip.global.exception.user.UserException;
import backend.zip.global.status.ErrorStatus;
import backend.zip.repository.UserRepository;
import backend.zip.repository.broker.BrokerItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrokerItemShowServiceImpl implements BrokerItemShowService {
    private final BrokerItemRepository brokerItemRepository;
    private final UserRepository userRepository;

    @Override
    public List<BrokerItem> findBrokerItemList(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        List<BrokerItem> brokerItemListByUser = brokerItemRepository.findBrokerItemByUser(userId);

        return brokerItemListByUser;
    }

    @Override
    public BrokerItem findBrokerItem(Long brokerItemId) {
        BrokerItem brokerItem = brokerItemRepository.findById(brokerItemId)
                .orElseThrow(() -> new BrokerItemException(ErrorStatus.BROKER_ITEM_NOT_FOUND));

        return brokerItem;
    }
}
