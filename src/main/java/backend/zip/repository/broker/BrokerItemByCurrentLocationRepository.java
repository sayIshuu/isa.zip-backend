package backend.zip.repository.broker;

import backend.zip.domain.broker.BrokerItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BrokerItemByCurrentLocationRepository extends JpaRepository<BrokerItem, Long> {
//    @Query(value = "select * from BrokerItem b " +
////            "join fetch b.brokerOption bo " +
////            "join fetch b.itemContent " +
////            "join fetch b.itemImages " +
//            "where ST_Distance_Sphere(point(:lng, :lat), point(x, y)) <= 2000", nativeQuery = true)

    List<BrokerItem> findBrokerItemsWithinRadius(@Param("lat") Double lat, @Param("lng") Double lng);
}
