package backend.zip.dto.useritem.response;

import backend.zip.domain.user.UserItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
public class UserItemByDongResponse {
    private String dong;
    private UserItemResponse userItemResponse;

    public UserItemByDongResponse(String dong, UserItemResponse userItemResponse) {
        this.dong = dong;
        this.userItemResponse = userItemResponse;
    }

    public static List<UserItemByDongResponse> from(Map<String, List<UserItem>> userItemByDong) {
        return userItemByDong.entrySet().stream()
                .map(entry -> {
                    UserItemResponse userItemResponse = UserItemResponse.from(entry.getValue());
                    return new UserItemByDongResponse(entry.getKey(), userItemResponse);
                })
                .toList();
    }
}
