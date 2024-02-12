package backend.zip.dto.useritem.request;

import backend.zip.domain.enums.*;
import backend.zip.domain.user.UserOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddUserItemOptionsRequest {
    private List<RoomType> roomType;
    private List<DealType> dealTypes;
    private Map<DealType, DealTypeInfo> dealInfoMap;
    private List<RoomSize> roomSize;
    private List<Floor> floor;
    private List<ManagementOption> managementOption;
    private List<InternalFacility> internalFacility;
    private ApproveDate approveDate;
    private List<ExtraFilter> extraFilter;

    @Getter
    @Setter
    public static class DealTypeInfo { //타입에 따라 null 허용
        private String minPrice;
        private String maxPrice;
        private String minMonthPrice;
        private String maxMonthPrice;
    }
}
