package backend.zip.service.match;

import backend.zip.domain.match.Matching;

import java.util.List;

public interface MatchService {
    List<Matching> matchBrokerItemsToUserItem(Long userItemId, List<Long> brokerItemId);
}
