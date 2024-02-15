package backend.zip.dto.useritem.response;

import backend.zip.domain.user.UserItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class UserItemByDongResponse {
    private String dong;
    private List<UserItemResponse> userItemResponses;

    public UserItemByDongResponse(String dong, List<UserItemResponse> userItemResponses) {
        this.dong = dong;
        this.userItemResponses = userItemResponses;
    }

    public static UserItemByDongResponse from(String dongName,List<UserItem> userItems) {
        return new UserItemByDongResponse(
                dongName,
                userItems.stream()
                        .map(UserItemResponse::from)
                        .collect(Collectors.toList())
        );
    }

    /*
    public static List<UserItemByDongResponse> from(Map<String, List<UserItem>> userItemByDong) {
        return userItemByDong.entrySet().stream()
                .map(entry -> new UserItemByDongResponse(
                        entry.getKey(),
                        entry.getValue().stream()
                                .map(UserItemResponse::from)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
     */
}
