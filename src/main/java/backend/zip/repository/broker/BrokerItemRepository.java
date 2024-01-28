package backend.zip.repository.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrokerItemRepository extends JpaRepository<BrokerItem, Long> {
    //UserID(BrokerID)에 따른 매물 조회
    List<BrokerItem> findAllByUser(User user);

    //동에 따른 전체 매물 요청 조회
    List<BrokerItem> findByDong(String dong);

}
