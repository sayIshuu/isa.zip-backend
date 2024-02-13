package backend.zip.domain.broker;

import backend.zip.domain.broker.options.*;
import backend.zip.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Setter
//@DynamicInsert
//@DynamicUpdate
public class BrokerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_option_id")
    private Long brokerOptionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "broker_room_type")
    private RoomType roomType;

    @Column(name = "broker_room_size")
    private String roomSize;

    @Column(name = "broker_approved_date")
    @Enumerated(EnumType.STRING)
    private ApproveDate approvedDate;

    @OneToOne(mappedBy = "brokerOption", cascade = CascadeType.ALL)
    private BrokerItem brokerItem;

    @OneToMany(mappedBy = "brokerOption", cascade = CascadeType.ALL)
    @BatchSize(size = 10)
    private List<BrokerFloor> brokerFloors;

    @OneToMany(mappedBy = "brokerOption", cascade = CascadeType.ALL)
    @BatchSize(size = 10)
    private List<BrokerDealType> brokerDealTypes;

    @OneToMany(mappedBy = "brokerOption", cascade = CascadeType.ALL)
    @BatchSize(size = 10)
    private List<BrokerManagementOption> brokerManagementOptions;

    @OneToMany(mappedBy = "brokerOption", cascade = CascadeType.ALL)
    @BatchSize(size = 10)
    private List<BrokerInternalFacility> brokerInternalFacilities;

    @OneToMany(mappedBy = "brokerOption", cascade = CascadeType.ALL)
    @BatchSize(size = 10)
    private List<BrokerExtraFilter> brokerExtraFilters;

    public void setBrokerItem(BrokerItem brokerItem) {
        this.brokerItem = brokerItem;
    }

}
