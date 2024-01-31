package backend.zip.dto.brokeritem.response;

import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.broker.options.BrokerDealType;
import backend.zip.domain.broker.options.BrokerExtraFilter;
import backend.zip.domain.broker.options.BrokerFloor;
import backend.zip.domain.broker.options.BrokerInternalFacility;
import backend.zip.domain.enums.ApproveDate;
import backend.zip.domain.enums.Floor;
import backend.zip.domain.enums.RoomType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
public class BrokerItemOptionResponse {
    private Long brokerOptionId;
//    private Long brokerItemId;
    private List<BrokerDealType> dealTypes;
    private RoomType roomType;
    private String roomSize;
//    private Integer managementPrice;
    private List<BrokerFloor> floors;
//    private String customFloor;
    private List<BrokerInternalFacility> internalFacilities;
    private List<BrokerExtraFilter> extraFilters;
    private ApproveDate approvedDate;


    public BrokerItemOptionResponse(BrokerOption brokerOption) {
        this.brokerOptionId = brokerOption.getBrokerOptionId();
        this.dealTypes = brokerOption.getBrokerDealTypes();
        this.roomType = brokerOption.getRoomType();
        this.roomSize = brokerOption.getRoomSize();
//        this.managementPrice = managementPrice;
        this.floors = brokerOption.getBrokerFloors();
//        this.customFloor = brokerOption.getCustomFloor();
        this.internalFacilities = brokerOption.getBrokerInternalFacilities();
        this.extraFilters = brokerOption.getBrokerExtraFilters();
        this.approvedDate = brokerOption.getApprovedDate();
    }
}
