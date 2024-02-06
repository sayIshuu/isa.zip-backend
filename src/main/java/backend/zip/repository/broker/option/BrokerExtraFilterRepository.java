package backend.zip.repository.broker.option;

import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.broker.options.BrokerExtraFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BrokerExtraFilterRepository extends JpaRepository<BrokerExtraFilter, Long> {
    @Modifying
    @Query("DELETE FROM BrokerExtraFilter bef WHERE bef.brokerOption = :brokerOption")
    public void deleteByBrokerOption(@Param("brokerOption") BrokerOption brokerOption);
}