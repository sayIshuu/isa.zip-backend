package backend.zip.service;

import backend.zip.domain.user.User;
import backend.zip.dto.auth.response.AuthResponse;
import backend.zip.dto.user.request.UserRequest;
import backend.zip.dto.user.response.UserResponse;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.global.exception.user.UserNotFoundException;
import backend.zip.repository.UserRepository;
import backend.zip.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static backend.zip.global.status.ErrorStatus.USER_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void logout() {
        refreshTokenRedisService.deleteRefreshToken(Long.parseLong(SecurityUtils.getLoggedInUserId()));
    }

    @Override
    public void deleteUser() {
        User user = userRepository.findById(Long.parseLong(SecurityUtils.getLoggedInUserId()))
                .orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND));

        refreshTokenRedisService.deleteRefreshToken(user.getId());
        userRepository.delete(user);
    }

    @Override
    public UserResponse.ProfileResponse getProfile() {
        User user = userRepository.findById(Long.parseLong(SecurityUtils.getLoggedInUserId()))
                .orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND));
        return UserResponse.ProfileResponse.builder()
                .id(user.getId())
                .userImg(user.getUserImg())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public UserResponse.ProfileResponse updateProfile(UserRequest.ProfileRequest profileRequest) {
        User user = userRepository.findById(Long.parseLong(SecurityUtils.getLoggedInUserId()))
                .orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND));

        if(profileRequest.getNickName() != null && !profileRequest.getNickName().isEmpty()) {
            user.updateNickName(profileRequest.getNickName());
        }

        if(profileRequest.getPassword() != null && !profileRequest.getPassword().isEmpty()) {
            user.updatePassword(passwordEncoder.encode(profileRequest.getPassword()));
        }

        return UserResponse.ProfileResponse.builder()
                .id(user.getId())
                .userImg(user.getUserImg())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .build();
    }
}
