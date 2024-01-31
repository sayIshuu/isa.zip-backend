package backend.zip.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthResponse {
    @Getter
    public static class LoginResponse{
        private final Long id;
        private final String accessToken;
        private final String refreshToken;

        @Builder
        public LoginResponse(Long id, String accessToken, String refreshToken){
            this.id = id;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}
