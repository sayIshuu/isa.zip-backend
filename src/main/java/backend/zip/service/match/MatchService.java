package backend.zip.service.match;

import backend.zip.domain.enums.MatchStatus;
import backend.zip.domain.match.Matching;
import backend.zip.dto.match.response.MatchItemAllByUserResponse;

import java.util.List;

public interface MatchService {
    List<Matching> matchBrokerItemsToUserItem(Long userItemId, List<Long> brokerItemId);


    List<MatchItemAllByUserResponse> getMatchItemsByStatus(Long userId, MatchStatus matchStatus);
}
