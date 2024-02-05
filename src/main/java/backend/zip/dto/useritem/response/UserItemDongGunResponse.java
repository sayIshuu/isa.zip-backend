package backend.zip.dto.useritem.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
public class UserItemDongGunResponse {
    private String dong;
    private Long dongCount;

    public UserItemDongGunResponse(String dong, Long dongCount) {
        this.dong = dong;
        this.dongCount = dongCount;
    }

    public static List<UserItemDongGunResponse> from(Map<String, Long> dongCountMap) {
        return dongCountMap.entrySet().stream()
                .map(entry -> new UserItemDongGunResponse(entry.getKey(), entry.getValue()))
                .toList();
    }
}
