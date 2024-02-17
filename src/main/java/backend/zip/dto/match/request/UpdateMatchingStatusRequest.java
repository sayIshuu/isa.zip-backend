package backend.zip.dto.match.request;

import backend.zip.domain.enums.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateMatchingStatusRequest {
    private List<Long> matchingIds;
    private MatchStatus matchStatus;
}
