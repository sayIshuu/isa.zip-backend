package backend.zip.domain.broker;

import backend.zip.domain.broker.options.*;
import backend.zip.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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
    private List<BrokerFloor> brokerFloors = new ArrayList<>();

    @OneToMany(mappedBy = "brokerOption", cascade = CascadeType.ALL)
    private List<BrokerDealType> brokerDealType = new ArrayList<>();

    @OneToMany(mappedBy = "brokerOption", cascade = CascadeType.ALL)
    private List<BrokerManagementOption> brokerManagementOptions = new ArrayList<>();

    @OneToMany(mappedBy = "brokerOption", cascade = CascadeType.ALL)
    private List<BrokerInternalFacility> brokerInternalFacilities = new ArrayList<>();

    @OneToMany(mappedBy = "brokerOption", cascade = CascadeType.ALL)
    private List<BrokerExtraFilter> brokerExtraFilters = new ArrayList<>();

    public void setBrokerItem(BrokerItem brokerItem) {
        this.brokerItem = brokerItem;
    }

}
