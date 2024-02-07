package backend.zip.service;

import backend.zip.domain.user.User;
import backend.zip.dto.auth.request.AuthRequest;
import backend.zip.dto.user.request.UserRequest;
import backend.zip.dto.user.response.UserResponse;
import backend.zip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserResponse.ProfileResponse getProfile();
    UserResponse.ProfileResponse updateProfile(MultipartFile userImg, String nickName, String password);
    public void logout();
    void deleteUser();
    void authenticateBroker(AuthRequest.BrokerRequest brokerRequest);
}
