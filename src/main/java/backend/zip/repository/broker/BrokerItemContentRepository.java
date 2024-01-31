package backend.zip.repository.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.item.ItemContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrokerItemContentRepository extends JpaRepository<ItemContent, Long> {
}
