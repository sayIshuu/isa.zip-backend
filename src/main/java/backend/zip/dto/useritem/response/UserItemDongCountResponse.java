package backend.zip.dto.useritem.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
public class UserItemDongCountResponse {
    private String dong;
    private Long dongCount;

    public UserItemDongCountResponse(String dong, Long dongCount) {
        this.dong = dong;
        this.dongCount = dongCount;
    }

    public static List<UserItemDongCountResponse> from(Map<String, Long> dongCountMap) {
        return dongCountMap.entrySet().stream()
                .map(entry -> new UserItemDongCountResponse(entry.getKey(), entry.getValue()))
                .toList();
    }
}
