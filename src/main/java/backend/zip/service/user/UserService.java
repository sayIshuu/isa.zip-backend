package backend.zip.service.user;

import backend.zip.dto.user.request.UserRequest;
import backend.zip.dto.user.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserResponse.ProfileResponse getProfile();
    UserResponse.ProfileResponse updateProfile(MultipartFile userImg, String nickName, String password);
    public void logout();
    void deleteUser();
    void authenticateBroker(UserRequest.BrokerRequest brokerRequest);
}
