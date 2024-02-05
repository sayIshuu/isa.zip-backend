package backend.zip.repository.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.item.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BrokerItemImageRepository extends JpaRepository<ItemImage,Long> {
    @Modifying
    @Query("DELETE FROM ItemImage ii WHERE ii.brokerItem = :brokerItem")
    void deleteByBrokerItem(@Param("brokerItem") BrokerItem brokerItem);

}
