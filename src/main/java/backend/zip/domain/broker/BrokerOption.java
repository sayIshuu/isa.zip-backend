package backend.zip.domain.broker;

import backend.zip.domain.broker.options.*;
import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Setter
//@DynamicInsert
//@DynamicUpdate
public class BrokerOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_option_id")
    private Long brokerOptionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;

    @Column(name = "broker_room_size")
    private String roomSize;

    @Column(name = "broker_custom_floor")
    private String customFloor;

    @Column(name = "broker_floor")
    private Floor floor;

//    @Column(name = "management_price")
//    private Integer managementPrice;

    @Column(name = "broker_approved_date")
    @Enumerated(EnumType.STRING)
    private ApproveDate approvedDate;

    @OneToOne(mappedBy = "brokerOption", cascade = CascadeType.ALL)
    @JsonBackReference
    private BrokerItem brokerItem;

    @OneToMany(mappedBy = "brokerOption", cascade = CascadeType.ALL)
    private List<BrokerFloor> brokerFloors;

    @OneToMany(mappedBy = "brokerOption", cascade = CascadeType.ALL)
    private List<BrokerDealType> brokerDealTypes;

    @OneToMany(mappedBy = "brokerOption", cascade = CascadeType.ALL)
    private List<BrokerManagementOption> brokerManagementOptions;

    @OneToMany(mappedBy = "brokerOption", cascade = CascadeType.ALL)
    private List<BrokerInternalFacility> brokerInternalFacilities;

    @OneToMany(mappedBy = "brokerOption", cascade = CascadeType.ALL)
    private List<BrokerExtraFilter> brokerExtraFilters;

    public void setBrokerItem(BrokerItem brokerItem) {
        this.brokerItem = brokerItem;
    }

}
