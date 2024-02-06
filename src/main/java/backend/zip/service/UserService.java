package backend.zip.service;

import backend.zip.domain.user.User;
import backend.zip.dto.user.request.UserRequest;
import backend.zip.dto.user.response.UserResponse;
import backend.zip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    UserResponse.ProfileResponse getProfile();

    UserResponse.ProfileResponse updateProfile(UserRequest.ProfileRequest profileRequest);

    public void logout();

    void deleteUser();
}
