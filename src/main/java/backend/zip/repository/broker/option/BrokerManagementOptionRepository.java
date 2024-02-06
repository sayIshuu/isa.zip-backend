package backend.zip.repository.broker.option;

import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.broker.options.BrokerManagementOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BrokerManagementOptionRepository extends JpaRepository<BrokerManagementOption, Long> {
    @Modifying
    @Query("DELETE FROM BrokerManagementOption bmo WHERE bmo.brokerOption = :brokerOption")
    public void deleteByBrokerOption(@Param("brokerOption") BrokerOption brokerOption);
}