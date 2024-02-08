package backend.zip.dto.match.response;

import backend.zip.domain.enums.MatchStatus;
import backend.zip.domain.match.Matching;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MatchStatusResponse {
    private Long matchId;
    private MatchStatus matchStatus;

    public static MatchStatusResponse of(Matching matching) {
        return MatchStatusResponse.builder()
                .matchId(matching.getMatchID())
                .matchStatus(matching.getMatchStatus())
                .build();
    }
}
