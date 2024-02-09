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
    private UserItemResponse userItemResponse;
    private List<BrokerItemResponse> brokerItemResponses;

    //생성자 필요없다. maybe

    public static List<MatchItemAllByUserResponse> from(Map<UserItem, List<Matching>> matchingsByUserItem) {
        return matchingsByUserItem.entrySet().stream()
                .map(entry -> new MatchItemAllByUserResponse(
                        entry.getKey().getDong(),
                        (long) entry.getValue().size(),
                        UserItemResponse.from(entry.getKey()), //얘가 되려나?
                        entry.getValue().stream()
                                .map(BrokerItemResponse::from)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}
