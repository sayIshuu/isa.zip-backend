package backend.zip.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthResponse {
    @Getter
    public static class LoginResponse{
        private final Long id;
        private final String nickName;
        private final String accessToken;
        private final String refreshToken;

        @Builder
        public LoginResponse(Long id, String nickName, String accessToken, String refreshToken){
            this.id = id;
            this.nickName = nickName;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}
