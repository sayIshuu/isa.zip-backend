package backend.zip.dto.useritem.response;

import backend.zip.domain.user.UserItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class UserItemResponse {
    private Long userItemId;
    private Long userId;
    private String userNickname;
    private UserItemAddressResponse userItemAddressResponse;
    private UserItemOptionResponse userItemOptionsResponse;

    public UserItemResponse(Long userItemId,
                            Long userId,
                            String userNickname,
                            UserItemAddressResponse userItemAddressResponse,
                            UserItemOptionResponse userItemOptionsResponse) {
        this.userItemId = userItemId;
        this.userId = userId;
        this.userNickname = userNickname;
        this.userItemAddressResponse = userItemAddressResponse;
        this.userItemOptionsResponse = userItemOptionsResponse;
    }

    public static UserItemResponse from(UserItem value) {
        return new UserItemResponse(
                value.getUserItemId(),
                value.getUser().getId(),
                value.getUser().getNickName(),
                UserItemAddressResponse.from(value.getAddress(), value.getDong()),
                UserItemOptionResponse.from(
                        value.getUserOption().getUserOptionId(),
                        value.getUserOption().getUserRoomTypes(),
                        value.getUserOption().getUserDealTypes(),
                        value.getUserOption().getUserRoomSizes(),
                        value.getUserOption().getUserFloors(),
                        value.getUserOption().getUserManagementOptions(),
                        value.getUserOption().getUserInternalFacilities(),
                        value.getUserOption().getApproveDate(),
                        value.getUserOption().getUserExtraFilters())
        );
    }

    /*
                value.get(0).getUserItemId(),
                value.get(0).getUser().getId(), //이런식으로 조인되서 데이터 가져옴 성능 괜찮나
                value.get(0).getUser().getNickName(),
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

                 */
}
