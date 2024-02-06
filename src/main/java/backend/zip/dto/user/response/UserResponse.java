package backend.zip.dto.user.response;

import lombok.Builder;
import lombok.Getter;

public class UserResponse {
    @Getter
    public static class ProfileResponse{
        private final Long id;
        private final String userImg;
        private final String nickName;
        private final String email;

        @Builder
        public ProfileResponse(Long id, String userImg, String nickName, String email){
            this.id = id;
            this.userImg = userImg;
            this.nickName = nickName;
            this.email = email;
        }
    }
}