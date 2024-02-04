package backend.zip.repository.broker.option;

import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.broker.options.BrokerDealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BrokerDealTypeRepository extends JpaRepository<BrokerDealType, Long> {
    @Modifying
    @Query("DELETE FROM BrokerDealType bdt WHERE bdt.brokerOption = :brokerOption")
    public void deleteByBrokerOption(@Param("brokerOption") BrokerOption brokerOption);
}
