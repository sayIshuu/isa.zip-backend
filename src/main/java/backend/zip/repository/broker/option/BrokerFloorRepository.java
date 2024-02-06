package backend.zip.repository.broker.option;

import backend.zip.domain.broker.BrokerOption;

import backend.zip.domain.broker.options.BrokerFloor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BrokerFloorRepository extends JpaRepository<BrokerFloor, Long> {
    @Modifying
    @Query("DELETE FROM BrokerFloor bf WHERE bf.brokerOption = :brokerOption")
    public void deleteByBrokerOption(@Param("brokerOption") BrokerOption brokerOption);
}