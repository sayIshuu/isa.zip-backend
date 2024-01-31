package backend.zip.repository.broker;

import backend.zip.domain.item.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrokerItemImageRepository extends JpaRepository<ItemImage,Long> {
}
