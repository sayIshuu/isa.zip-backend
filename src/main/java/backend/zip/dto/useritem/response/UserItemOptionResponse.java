package backend.zip.dto.useritem.response;

import backend.zip.domain.enums.ApproveDate;
import backend.zip.domain.user.UserOption;
import backend.zip.domain.user.options.*;
import backend.zip.dto.useritem.request.AddUserItemOptionsRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
public class UserItemOptionResponse {
    private Long userOptionId;
    private List<UserRoomType> userRoomTypes;
    private List<UserDealType> userDealTypes;
    private List<UserRoomSize> userRoomSizes;
    private List<UserFloor> userFloors;
    private List<UserManagementOption> userManagementOptions;
    private List<UserInternalFacility> userInternalFacilities;
    private ApproveDate approveDate;
    private List<UserExtraFilter> userExtraFilters;

    public UserItemOptionResponse(Long userOptionId,
                                  List<UserRoomType> userRoomTypes,
                                  List<UserDealType> userDealTypes,
                                  List<UserRoomSize> userRoomSizes,
                                  List<UserFloor> userFloors,
                                  List<UserManagementOption> userManagementOptions,
                                  List<UserInternalFacility> userInternalFacilities,
                                  ApproveDate approveDate,
                                  List<UserExtraFilter> userExtraFilters) {
        this.userOptionId = userOptionId;
        this.userRoomTypes = userRoomTypes;
        this.userDealTypes = userDealTypes;
        this.userRoomSizes = userRoomSizes;
        this.userFloors = userFloors;
        this.userManagementOptions = userManagementOptions;
        this.userInternalFacilities = userInternalFacilities;
        this.approveDate = approveDate;
        this.userExtraFilters = userExtraFilters;
    }

    public static UserItemOptionResponse from(Long userOptionId,
                                              List<UserRoomType> userRoomTypes,
                                              List<UserDealType> userDealTypes,
                                              List<UserRoomSize> userRoomSizes,
                                              List<UserFloor> userFloors,
                                              List<UserManagementOption> userManagementOptions,
                                              List<UserInternalFacility> userInternalFacilities,
                                              ApproveDate approveDate,
                                              List<UserExtraFilter> userExtraFilters) {

        return new UserItemOptionResponse(userOptionId,
                userRoomTypes,
                userDealTypes,
                userRoomSizes,
                userFloors,
                userManagementOptions,
                userInternalFacilities,
                approveDate,
                userExtraFilters);
    }
}

