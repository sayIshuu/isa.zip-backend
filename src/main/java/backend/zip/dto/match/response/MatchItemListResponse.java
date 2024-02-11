package backend.zip.dto.match.response;

import backend.zip.domain.enums.MatchStatus;
import backend.zip.domain.match.Matching;
import backend.zip.dto.brokeritem.response.BrokerItemResponse;
import backend.zip.dto.useritem.response.UserItemResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class MatchItemListResponse {
    private List<MatchListDetails> matchListDetails;

    @Getter
    @AllArgsConstructor
    public static class MatchListDetails {
        private Long matchId;
        private MatchStatus matchStatus;
        private UserItemResponse userItemResponse;
        private BrokerItemResponse brokerItemResponse;
    }

    public static MatchItemListResponse of(List<Matching> matchingList) {
        List<MatchListDetails> matchListDetailsList = matchingList.stream()
                .map(matching -> new MatchListDetails(
                        matching.getMatchID(),
                        matching.getMatchStatus(),
                        UserItemResponse.from(matching.getUserItem()),
                        BrokerItemResponse.of(matching.getBrokerItem())
                )).collect(Collectors.toList());

        return new MatchItemListResponse(matchListDetailsList);
    }
}
