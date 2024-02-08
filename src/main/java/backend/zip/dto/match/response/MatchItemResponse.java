package backend.zip.dto.match.response;

import backend.zip.domain.match.Matching;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class MatchItemResponse {
    private List<MatchDetail> matchDetails;

    @Getter
    @AllArgsConstructor
    public static class MatchDetail {
        private Long matchId;
        private Long userItemId;
        private Long brokerItemId;
    }

    public static MatchItemResponse of(List<Matching> matchingList) {
        List<MatchDetail> matchDetails = matchingList.stream()
                .map(matching -> new MatchDetail(
                        matching.getMatchID(),
                        matching.getUserItem().getUserItemId(),
                        matching.getBrokerItem().getBrokerItemId()))
                .collect(Collectors.toList());

        return new MatchItemResponse(matchDetails);
    }
}
