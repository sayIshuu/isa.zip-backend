package backend.zip.repository.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.broker.BrokerOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrokerOptionRepository extends JpaRepository<BrokerOption, Long> {

}
