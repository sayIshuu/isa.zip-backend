package backend.zip.service;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.user.User;
import backend.zip.repository.BrokerItemRepository;
import backend.zip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrokerItemService {
    private final BrokerItemRepository brokerItemRepository;
    private final UserRepository userRepository;

    //UserID(BrokerID)에 따른 매물 조회
    public List<BrokerItem> getAllBrokerItemByUser(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));

        List<BrokerItem> allBrokerItemByUser = brokerItemRepository.findAllByUser(findUser);
//        if (allBrokerItemByUser.isEmpty()) {
//            throw new IllegalArgumentException("해당 사용자에게 연관된 매물이 존재하지 않습니다.");
//        }
        return allBrokerItemByUser;
    }

    //...


}
