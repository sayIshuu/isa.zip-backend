package backend.zip.repository.broker;

import backend.zip.domain.broker.BrokerItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BrokerItemByCurrentLocationRepository extends JpaRepository<BrokerItem, Long> {
    @Query(value = "select * from broker_item where ST_Distance_Sphere(point(:lng,:lat),point(x,y)) <= 2000",nativeQuery = true)
    List<BrokerItem> findBrokerItemsWithinRadius(@Param("lng") Double lng, @Param("lat") Double lat);
}
