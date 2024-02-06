package backend.zip.dto.useritem.response;

import backend.zip.domain.user.UserItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class UserItemResponse {
    private Long userItemId;
    private UserItemAddressResponse userItemAddressResponse;
    private UserItemOptionResponse userItemOptionsResponse;

    public UserItemResponse(Long userItemId,
                            UserItemAddressResponse userItemAddressResponse,
                            UserItemOptionResponse userItemOptionsResponse) {
        this.userItemId = userItemId;
        this.userItemAddressResponse = userItemAddressResponse;
        this.userItemOptionsResponse = userItemOptionsResponse;
    }

    public static UserItemResponse from(List<UserItem> value) {
        return new UserItemResponse(
                value.get(0).getUserItemId(),
                UserItemAddressResponse.from(value.get(0).getAddress(), value.get(0).getDong()),
                UserItemOptionResponse.from(
                        value.get(0).getUserOption().getUserOptionId(),
                        value.get(0).getUserOption().getUserRoomTypes(),
                        value.get(0).getUserOption().getUserDealTypes(),
                        value.get(0).getUserOption().getUserRoomSizes(),
                        value.get(0).getUserOption().getUserFloors(),
                        value.get(0).getUserOption().getUserManagementOptions(),
                        value.get(0).getUserOption().getUserInternalFacilities(),
                        value.get(0).getUserOption().getApproveDate(),
                        value.get(0).getUserOption().getUserExtraFilters())
        );
    }
}
