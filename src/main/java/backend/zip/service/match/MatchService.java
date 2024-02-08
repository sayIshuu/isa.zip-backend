package backend.zip.service.match;

import backend.zip.domain.match.Matching;

import java.util.List;

public interface MatchService {
    List<Matching> matchBrokerItemsToUserItem(Long userItemId, List<Long> brokerItemId);

    Matching findMatch(Long matchId);

    Matching updateMatchStatusToComplete(Long matchingId);

    List<Matching> findAllMatch();
}
