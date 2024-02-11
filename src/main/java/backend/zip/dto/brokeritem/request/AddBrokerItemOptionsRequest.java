package backend.zip.dto.brokeritem.request;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.broker.options.BrokerDealType;
import backend.zip.domain.broker.options.BrokerFloor;
import backend.zip.domain.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddBrokerItemOptionsRequest {
//    private Long brokerItemId;
    private RoomType roomType;
    private Set<DealType> dealTypes;
    private Map<DealType, DealInfo> dealInfoMap;
    private String roomSize;
    private List<Floor> selectedFloor;
    private String customFloor;
    private List<ManagementOption> managementOptions;
    private String managementPrice;
    private List<InternalFacility> internalFacilities;
    private ApproveDate approveDate;
    private List<ExtraFilter> extraFilters;

    @Getter
    @Setter
    public static class DealInfo {
        private String price;
        private String deposit;
        private String monthPrice;
    }

}
