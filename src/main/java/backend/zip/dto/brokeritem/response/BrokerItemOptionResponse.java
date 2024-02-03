package backend.zip.dto.brokeritem.response;

import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.broker.options.*;
import backend.zip.domain.enums.ApproveDate;
import backend.zip.domain.enums.RoomType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

//@Builder
@Getter
public class BrokerItemOptionResponse {
    private Long brokerOptionId;
    private List<BrokerDealType> dealTypes;
    private RoomType roomType;
    private String roomSize;
    private List<BrokerFloor> floors;
    private List<BrokerManagementOption> managementOptions;
    private List<BrokerInternalFacility> internalFacilities;
    private List<BrokerExtraFilter> extraFilters;
    private ApproveDate approvedDate;

    public BrokerItemOptionResponse(BrokerOption brokerOption) {
        this.brokerOptionId = brokerOption.getBrokerOptionId();
        this.dealTypes = brokerOption.getBrokerDealTypes();
        this.roomType = brokerOption.getRoomType();
        this.roomSize = brokerOption.getRoomSize();
        this.floors = brokerOption.getBrokerFloors();
        this.managementOptions = brokerOption.getBrokerManagementOptions();
        this.internalFacilities = brokerOption.getBrokerInternalFacilities();
        this.extraFilters = brokerOption.getBrokerExtraFilters();
        this.approvedDate = brokerOption.getApprovedDate();
    }

//    public static BrokerItemOptionResponse of(Long brokerOptionId, List<BrokerDealType> dealTypes,
//                                              RoomType roomType, String roomSize,
//                                              List<BrokerFloor> floors,
//                                              List<BrokerManagementOption> managementOptions,
//                                              List<BrokerInternalFacility> internalFacilities,
//                                              List<BrokerExtraFilter> extraFilters,
//                                              ApproveDate approveDate) {
//
//        return BrokerItemOptionResponse.builder()
//                .brokerOptionId(brokerOptionId)
//                .dealTypes(dealTypes)
//                .roomType(roomType)
//                .roomSize(roomSize)
//                .floors(floors)
//                .managementOptions(managementOptions)
//                .internalFacilities(internalFacilities)
//                .extraFilters(extraFilters)
//                .approvedDate(approveDate)
//                .build();
//    }

}
