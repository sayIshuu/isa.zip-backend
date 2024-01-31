package backend.zip.domain.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Token {
    private final TokenType tokenType;
    private final String token;
}
