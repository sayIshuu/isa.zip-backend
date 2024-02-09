package backend.zip.dto.match.response;

import backend.zip.domain.match.Matching;
import backend.zip.domain.user.UserItem;
import backend.zip.dto.brokeritem.response.BrokerItemResponse;
import backend.zip.dto.useritem.response.UserItemResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class MatchItemAllByUserResponse {
    private String dong;
    private Long count;
    private Long userItemId;
    private List<BrokerItemResponse> brokerItemResponses;

    public MatchItemAllByUserResponse(String dong, Long count, Long userItemId, List<BrokerItemResponse> brokerItemResponses) {
        this.dong = dong;
        this.count = count;
        this.userItemId = userItemId;
        this.brokerItemResponses = brokerItemResponses;
    }


    public static List<MatchItemAllByUserResponse> from(Map<UserItem, List<Matching>> matchingsByUserItem) {
        return matchingsByUserItem.entrySet().stream()
                .map(entry -> new MatchItemAllByUserResponse(
                        entry.getKey().getDong(),
                        (long) entry.getValue().size(),
                        entry.getKey().getUserItemId(),
                        entry.getValue().stream()
                                .map(BrokerItemResponse::from)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}
